package com.wang.sso.core.security.filter

import com.wang.sso.common.utils.TokenUtils
import com.wang.sso.core.consts.CommonConstant
import com.wang.sso.core.exception.ExceptionEnum
import com.wang.sso.core.exception.SsoException
import com.wang.sso.core.exception.SsoSecurityException
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
 *
 * @author FlowersPlants
 * @since v1
 */
class SecurityAuthorizationFilter(authenticationManager: AuthenticationManager) :
    BasicAuthenticationFilter(authenticationManager) {

    /**
     * 先取消tokenHeader判断，以后在前端加上token的时候添加JWT_TOKEN_HEADER
     */
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val tokenHeader = request.getHeader(CommonConstant.JWT_TOKEN_HEADER)
        if (tokenHeader == null) { // 先取消 || !tokenHeader.startsWith(CommonConstant.JWT_TOKEN_HEAD)) {
            chain.doFilter(request, response)
            throw SsoSecurityException(ExceptionEnum.AUTHORIZATION_FAIL)
            // return // 没有token，直接pass
        }
        SecurityContextHolder.getContext().authentication = getAuthentication(tokenHeader)
        super.doFilterInternal(request, response, chain)
    }

    /**
     * 解析token，返回AuthenticationToken；
     * 返回的不是一个SecurityUser对象，导致UserUtils中只能从json转化
     */
    private fun getAuthentication(tokenHeader: String): UsernamePasswordAuthenticationToken? {
//        val token = if (tokenHeader.contains(CommonConstant.JWT_TOKEN_HEAD)) {
//            tokenHeader.replace(CommonConstant.JWT_TOKEN_HEAD, "")
//        } else {
//            tokenHeader
//        }
        if (!TokenUtils.isExpiration(tokenHeader)) {
            val subject = TokenUtils.getSubjectFormToken(tokenHeader)
            if (subject != null && subject.toLowerCase() != "null") {
                val securityUser = TokenUtils.getUserBySubject(subject)
                return UsernamePasswordAuthenticationToken(subject, null, securityUser?.authorities)
            }
            throw SsoException(611, "凭证错误")
        }
        throw SsoSecurityException(ExceptionEnum.CREDENTIALS_EXPIRED)
    }
}