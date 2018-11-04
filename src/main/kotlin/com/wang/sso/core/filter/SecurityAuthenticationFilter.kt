package com.wang.sso.core.filter

import com.alibaba.fastjson.JSONObject
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
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
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        println(request!!.contentType)
        if (request.contentType == MediaType.APPLICATION_JSON_VALUE || request.contentType == MediaType.APPLICATION_JSON_UTF8_VALUE) {
            getParameters(request)

            val authRequest = UsernamePasswordAuthenticationToken(this.obtainUsername(request), this.obtainPassword(request))
            setDetails(request, authRequest)
        }
        return super.attemptAuthentication(request, response)
    }

    override fun obtainUsername(request: HttpServletRequest?): String {
        return if (paramsMap.isNotEmpty()) {
            paramsMap.getValue(PARAMETER_USERNAME).toString()
        } else {
            request!!.getParameter(PARAMETER_USERNAME)
        }
    }

    override fun obtainPassword(request: HttpServletRequest?): String {
        return if (paramsMap.isNotEmpty()) {
            paramsMap.getValue(PARAMETER_PASSWORD).toString()
        } else {
            request!!.getParameter(PARAMETER_PASSWORD)
        }
    }

    /**
     * 自定义登录参数名称
     */
    companion object {
        private const val PARAMETER_USERNAME = "account"
        private const val PARAMETER_PASSWORD = "pwd"
//        private const val PARAMETER_VALID_CODE = "validCode"
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
            val streamReader = BufferedReader(InputStreamReader(request.inputStream, "UTF-8"))
            val responseStrBuilder = StringBuilder()
            var inputStr: String? = ""
            while (inputStr != null) {
                responseStrBuilder.append(inputStr)
                inputStr = streamReader.readLine()
            }

            val jsonObject = JSONObject.parseObject(responseStrBuilder.toString())
            paramsMap.putAll(jsonObject)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}