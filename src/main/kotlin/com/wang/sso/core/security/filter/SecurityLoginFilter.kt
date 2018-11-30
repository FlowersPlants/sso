package com.wang.sso.core.security.filter

import com.wang.sso.common.utils.JsonUtils
import com.wang.sso.core.consts.CommonConstant
import com.wang.sso.core.exception.ExceptionEnum
import com.wang.sso.core.exception.SsoException
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 自定义登录过滤器，只管登录；获取json格式上传的数据（这种数据在request.getParameter方法中获取不到）
 * 可获取的参数包括：json格式和url拼接方式；优先取json格式的参数。
 * 可看 https://blog.csdn.net/mushuntaosama/article/details/78904863
 *
 * 自定义参数有以下想法：
 * 1、代码仿照UsernamePasswordAuthenticationFilter来写，完善类中的代码
 * 1、建议继承AbstractAuthenticationProcessingFilter重写方法，自定义token来保存认证信息
 *
 * @author FlowersPlants
 * @since v1
 */
class SecurityLoginFilter : UsernamePasswordAuthenticationFilter() {
    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        return if (request!!.contentType == MediaType.APPLICATION_JSON_VALUE || request.contentType == MediaType.APPLICATION_JSON_UTF8_VALUE) {
            getParameters(request)
            val username = this.obtainParameterValue(request, CommonConstant.PARAMETER_USERNAME)
            val password = this.obtainParameterValue(request, CommonConstant.PARAMETER_PASSWORD)
            val authRequest = UsernamePasswordAuthenticationToken(username, password)

            setDetails(request, authRequest)
//            authRequest.details = WebAuthenticationDetailsSource().buildDetails(request)
            this.authenticationManager.authenticate(authRequest)
        } else {
            super.attemptAuthentication(request, response)
        }
    }

    /**
     * 获取参数值，有很多自定义参数时
     */
    private fun obtainParameterValue(request: HttpServletRequest, paramKey: String): String {
        return if (paramsMap.isNotEmpty()) {
            paramsMap.getValue(paramKey).toString()
        } else {
            request.getParameter(paramKey)
        }
    }

    /**
     * 保存登录参数，考虑同步问题？？？
     */
    private val paramsMap = HashMap<String, Any>()

    /**
     * 获取json提交的参数的值，
     * 从输入流获取登录参数
     */
    @Suppress("UNCHECKED_CAST")
    private fun getParameters(request: HttpServletRequest) {
        try {
            val streamReader = BufferedReader(InputStreamReader(request.inputStream, Charset.defaultCharset()))
            val responseStrBuilder = StringBuilder()
            var inputStr: String? = ""
            while (inputStr != null) {
                responseStrBuilder.append(inputStr)
                inputStr = streamReader.readLine()
            }

            val json = JsonUtils.readValue(responseStrBuilder.toString(), Map::class.java)
            paramsMap.putAll(json as Map<out String, Any>)
        } catch (e: IOException) {
            throw SsoException(ExceptionEnum.SERVICE_EXCEPTION)
        }
    }
}