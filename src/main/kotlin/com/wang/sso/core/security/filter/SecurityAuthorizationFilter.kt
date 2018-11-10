package com.wang.sso.core.security.filter

import com.wang.sso.common.utils.TokenUtils
import com.wang.sso.core.consts.CommonConstant
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 自定义鉴权过滤器
 * https://blog.csdn.net/ech13an/article/details/80779973
 */
class SecurityAuthorizationFilter(authenticationManager: AuthenticationManager) :
    BasicAuthenticationFilter(authenticationManager) {

    /**
     * 先取消tokenHeader判断，以后在前端加上token的时候添加JWT_TOKEN_HEADER
     */
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val tokenHeader = request.getHeader(CommonConstant.JWT_TOKEN_HEADER)
        if (tokenHeader == null ){ // 先取消 || !tokenHeader.startsWith(CommonConstant.JWT_TOKEN_HEAD)) {
            chain.doFilter(request, response)
            return // 直接放行
        }
        SecurityContextHolder.getContext().authentication = getAuthentication(tokenHeader)
        super.doFilterInternal(request, response, chain)
    }

    /**
     * 解析token，返回AuthenticationToken
     */
    private fun getAuthentication(tokenHeader: String): UsernamePasswordAuthenticationToken? {
        val token = if (tokenHeader.contains(CommonConstant.JWT_TOKEN_HEAD)) {
            tokenHeader.replace(CommonConstant.JWT_TOKEN_HEAD, "")
        } else {
            tokenHeader
        }
        val subject = TokenUtils.getSubjectFormToken(token)
        if (subject != null) {
            val securityUser = TokenUtils.getUserBySubject(subject)
            return UsernamePasswordAuthenticationToken(subject, null, securityUser.authorities)
        }
        return null
    }
}