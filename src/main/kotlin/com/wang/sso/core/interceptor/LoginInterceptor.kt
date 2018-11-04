package com.wang.sso.core.interceptor

import com.alibaba.fastjson.JSONObject
import org.springframework.web.servlet.HandlerInterceptor
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 登录拦截器，
 * 没得吊用，要你有何用？？？
 */
class LoginInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.requestURI == "/auth/login") {
            val streamReader = BufferedReader(InputStreamReader(request.inputStream, "UTF-8"))
            val responseStrBuilder = StringBuilder()
            var inputStr: String? = ""
            while (inputStr != null) {
                responseStrBuilder.append(inputStr)
                inputStr = streamReader.readLine()
            }

            val jsonObject = JSONObject.parseObject(responseStrBuilder.toString())
            val param = jsonObject.toJSONString()
            println(param)
        }
        return super.preHandle(request, response, handler)
    }
}