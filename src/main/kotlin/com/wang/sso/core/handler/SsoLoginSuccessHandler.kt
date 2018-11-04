package com.wang.sso.core.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.wang.sso.common.dto.ResponseDto
import com.wang.sso.modules.sys.utils.UserUtils
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.stereotype.Service
import java.io.IOException
import java.io.PrintWriter
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 自定义登录成功处理器
 * 发现一个问题：登录成功后会重定向到"/"URL，导致报错："页面找不到"！！！待解决
 */
@Service
class SsoLoginSuccessHandler : SavedRequestAwareAuthenticationSuccessHandler() {
    @Throws(ServletException::class, IOException::class)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val user = UserUtils.getSecurityUser()
        println("用户：" + user.account + " 登录成功.")

        response.contentType = "application/json;charset=utf-8"
        val responseDto = ResponseDto().apply {
            data = user
        }
        val out: PrintWriter = response.writer
        out.write(ObjectMapper().writeValueAsString(responseDto))
        super.onAuthenticationSuccess(request, response, authentication)
    }
}