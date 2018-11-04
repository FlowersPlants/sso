package com.wang.sso.core.filter

import org.springframework.stereotype.Component
import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletResponse

@Component
@WebFilter(urlPatterns = ["/*"])
class CrosWebFilter : Filter {
    override fun destroy() {

    }

    override fun init(p0: FilterConfig?) {

    }

    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        val response = p1 as HttpServletResponse
        response.setHeader("Access-control-Allow-Origin", "/**")
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH")
        response.setHeader("Access-Control-Allow-Credentials", "true")
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type")
        p2!!.doFilter(p0, response)
    }
}