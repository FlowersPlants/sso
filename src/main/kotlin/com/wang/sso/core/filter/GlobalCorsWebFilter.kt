package com.wang.sso.core.filter

import org.springframework.core.Ordered
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 解决跨域问题，可动态设置allow-origin
 */
class GlobalCorsWebFilter : OncePerRequestFilter(), Ordered {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val method = HttpMethod.resolve(request.method)
        logger.debug("doFilterInternal  url:${request.requestURI} ,method:$method")
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"))
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH")
        response.setHeader("Access-Control-Allow-Credentials", "true")
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"))
        if (method != HttpMethod.POST && method != HttpMethod.PUT) {
            if (method == HttpMethod.OPTIONS) {
                response.status = HttpStatus.OK.value()
                response.flushBuffer()
                return
            }
        } else {
            response.setHeader("Access-Control-Expose-Headers", "Location")
        }

        filterChain.doFilter(request, response)
    }

    override fun getOrder(): Int {
        return Ordered.HIGHEST_PRECEDENCE
    }
}
