package com.wang.sso.core.filter

import com.alibaba.fastjson.JSONObject
import com.wang.sso.core.exception.ExceptionEnum
import com.wang.sso.core.exception.SsoException
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 自定义登录参数，获取json格式上传的数据（这种数据在request.getParameter方法中获取不到）
 * 可获取的参数包括：json格式和url拼接方式；优先取json格式的参数。
 * 代码可仿照UsernamePasswordAuthenticationFilter来写
 * 可看 https://blog.csdn.net/mushuntaosama/article/details/78904863
 *
 * 可根据自己需求修改代码
 */
class SecurityAuthenticationFilter : UsernamePasswordAuthenticationFilter() {
    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        return if (request!!.contentType == MediaType.APPLICATION_JSON_VALUE || request.contentType == MediaType.APPLICATION_JSON_UTF8_VALUE) {
            getParameters(request)

            System.err.println("obtainParameterValue方法执行结果：" + obtainParameterValue(request, PARAMETER_REMEMBER_ME))
            val username = this.obtainParameterValue(request, PARAMETER_USERNAME)
            val password = this.obtainParameterValue(request, PARAMETER_PASSWORD)
            val authRequest = UsernamePasswordAuthenticationToken(username, password)
            setDetails(request, authRequest)
            this.authenticationManager.authenticate(authRequest)
        } else {
            super.attemptAuthentication(request, response)
        }
    }

//    override fun obtainUsername(request: HttpServletRequest): String {
//        return if (paramsMap.isNotEmpty()) {
//            paramsMap.getValue(PARAMETER_USERNAME).toString()
//        } else {
//            request.getParameter(PARAMETER_USERNAME)
//        }
//    }
//
//    override fun obtainPassword(request: HttpServletRequest): String {
//        return if (paramsMap.isNotEmpty()) {
//            paramsMap.getValue(PARAMETER_PASSWORD).toString()
//        } else {
//            request.getParameter(PARAMETER_PASSWORD)
//        }
//    }

    /**
     * 获取参数值，有很多自定义参数时，可重构原获取方式
     */
    private fun obtainParameterValue(request: HttpServletRequest, paramKey: String): String {
        return if (paramsMap.isNotEmpty()) {
            paramsMap.getValue(paramKey).toString()
        } else {
            request.getParameter(paramKey)
        }
    }

    /**
     * 自定义登录参数名称
     * <p>account：登录账号，pwd：登录密码，rememberMe：记住我，validateCode：验证码，mobile：手机号</p>
     */
    companion object {
        private const val PARAMETER_USERNAME = "account"
        private const val PARAMETER_PASSWORD = "pwd"
        private const val PARAMETER_REMEMBER_ME = "rememberMe" // 暂时没有使用
//        private const val PARAMETER_VALIDATE_CODE = "validateCode"
//        private const val PARAMETER_MOBILE = "mobile"
    }

    /**
     * 保存登录参数，考虑同步问题？？？
     */
    private val paramsMap = HashMap<String, Any>()

    /**
     * 获取json提交的参数的值
     */
    private fun getParameters(request: HttpServletRequest) {
        try {
            val streamReader = BufferedReader(InputStreamReader(request.inputStream, Charset.defaultCharset()))
            val responseStrBuilder = StringBuilder()
            var inputStr: String? = ""
            while (inputStr != null) {
                responseStrBuilder.append(inputStr)
                inputStr = streamReader.readLine()
            }

            val jsonObject = JSONObject.parseObject(responseStrBuilder.toString())
            paramsMap.putAll(jsonObject)
        } catch (e: IOException) {
            throw SsoException(ExceptionEnum.SERVICE_EXCEPTION)
        }
    }
}