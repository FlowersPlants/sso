package com.wang.sso.core.security.handler

import com.wang.sso.common.dto.ResponseDto
import com.wang.sso.common.utils.JsonUtils
import com.wang.sso.core.exception.ExceptionEnum
import org.springframework.http.HttpStatus
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
 * @author FlowersPlants
 * @since v1
 */
@Service
class SsoFailureHandler : SimpleUrlAuthenticationFailureHandler() {
    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        response.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
        val body = ResponseDto().apply {
            // 前端不输出code，所以此处不设置code的值
            var msg = exception.message
            if (exception.javaClass.simpleName == "BadCredentialsException") {
                msg = ExceptionEnum.USERNAME_OR_PASSWORD_INCORRECT.message
            }
            data = msg
        }
        val out: PrintWriter = response.writer
        out.write(JsonUtils.toJson(body))
    }
}