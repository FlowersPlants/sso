package com.wang.sso.core.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.wang.sso.common.dto.ResponseDto
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Service
import java.io.IOException
import java.io.PrintWriter
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 自定义登录失败处理器
 * 同样的问题，可以从SsoLoginSuccessHandler类获取解决方案
 */
@Service
class SsoFailureHandler : SimpleUrlAuthenticationFailureHandler() {
    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        System.err.println("login fail.")
        response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
        val responseDto = ResponseDto().apply {
            code = 500
            data = "login fail."
        }
        val out: PrintWriter = response.writer
        out.write(ObjectMapper().writeValueAsString(responseDto))
        // super.onAuthenticationFailure(request, response, exception)
    }
}