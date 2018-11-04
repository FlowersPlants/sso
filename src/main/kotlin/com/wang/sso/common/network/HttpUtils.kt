package com.wang.sso.common.network

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * http 工具类
 */
object HttpUtils {

    fun getRequest(): HttpServletRequest {
        return (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
    }

    fun getResponse(): HttpServletResponse? {
        return (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).response
    }

    fun getIp(): String {
        return getRequest().remoteHost
    }
}