package com.wang.sso.core.security.handler

import com.wang.sso.common.utils.JsonUtils
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import java.io.PrintWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 处理权限不足
 */
class SsoAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(req: HttpServletRequest, res: HttpServletResponse, e: AccessDeniedException) {
        res.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
        val out: PrintWriter = res.writer
        out.write(JsonUtils.toJson("权限不足！"))
    }
}
