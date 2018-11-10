package com.wang.sso.core.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.wang.sso.common.dto.ResponseDto
import com.wang.sso.common.utils.TokenUtils
import com.wang.sso.modules.sys.utils.UserUtils
import org.springframework.http.MediaType
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
 * 解决方案之一：可以借鉴AbstractAuthenticationTargetUrlRequestHandler类的源码
 * 解决方案二：从https://blog.csdn.net/qq_37502106/article/details/81045773获取解决方案，方法结尾不调用父类方法
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
        response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
        val body = ResponseDto().apply {
            data = TokenUtils.generateToken(ObjectMapper().writeValueAsString(user))
        }
        val out: PrintWriter = response.writer
        out.write(ObjectMapper().writeValueAsString(body))

        // 调用父类的方法会默认跳转，来自：https://blog.csdn.net/qq_37502106/article/details/81045773
        // super.onAuthenticationSuccess(request, response, authentication)
    }
}