package com.wang.sso.modules.sys.service.impl

import com.wang.sso.core.security.user.SecurityToken
import com.wang.sso.core.security.user.SecurityUser
import com.wang.sso.modules.sys.service.TokenService
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

/**
 * jwt方式token，默认
 * https://www.jb51.net/article/112146.htm
 */
@Service
@Primary
class TokenServiceJwtImpl : TokenService {
    override fun saveToken(securityUser: SecurityUser): SecurityToken {
        return SecurityToken()
    }

    override fun refresh(securityUser: SecurityUser) {
    }

    override fun deleteToken(token: String): Boolean? {
        return false
    }
}