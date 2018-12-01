package com.wang.sso.core.security.filter

import com.wang.sso.core.security.user.TokenUtils
import com.wang.sso.core.consts.CommonConstant
import com.wang.sso.core.exception.ExceptionEnum
import com.wang.sso.core.exception.SsoSecurityException
import com.wang.sso.core.security.user.SecurityUserFactory
import com.wang.sso.modules.sys.entity.User
import com.wang.sso.modules.sys.utils.UserUtils
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
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
            return // 没有token时直接返回，而不是抛出异常
        }
        SecurityContextHolder.getContext().authentication = getAuthentication(tokenHeader)
        super.doFilterInternal(request, response, chain)
    }

    /**
     * 解析token，返回AuthenticationToke
     * 返回的不是一个SecurityUser对象，导致SecurityUtils中只能从json转化
     */
    private fun getAuthentication(tokenHeader: String): Authentication? {
        if (!TokenUtils.isExpiration(tokenHeader)) {
            val account = TokenUtils.getSubjectFormToken(tokenHeader)
            // 需要从缓存获取信息，否则导则每个请求都会执行此sql查询操作
            val user = UserUtils.findUserByAccount(account) ?: User()
            val securityUser = SecurityUserFactory.create(user)
            return UsernamePasswordAuthenticationToken(securityUser, null, securityUser.authorities)
        }
        throw SsoSecurityException(ExceptionEnum.CREDENTIALS_EXPIRED)
    }
}