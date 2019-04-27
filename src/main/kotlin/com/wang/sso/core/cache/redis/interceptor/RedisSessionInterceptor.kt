package com.wang.sso.core.cache.redis.interceptor

import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 登录状态拦截器RedisSessionInterceptor
 */
class RedisSessionInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val session = request.session
//        if (ext){
//            return true
//        }
        return false
    }
}
