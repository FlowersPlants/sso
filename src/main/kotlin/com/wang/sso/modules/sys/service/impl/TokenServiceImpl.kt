package com.wang.sso.modules.sys.service.impl

import com.wang.sso.core.security.SecurityToken
import com.wang.sso.core.security.SecurityUser
import com.wang.sso.modules.sys.service.TokenService
import org.springframework.security.core.token.DefaultToken
import org.springframework.security.core.token.Token
import org.springframework.stereotype.Service

/**
 * 一般的token
 */
@Service
class TokenServiceImpl : TokenService, org.springframework.security.core.token.TokenService {
    override fun saveToken(securityUser: SecurityUser): SecurityToken {
        return SecurityToken()
    }

    override fun refresh(securityUser: SecurityUser) {
    }

    override fun deleteToken(token: String): Boolean? {
        return false
    }

    override fun verifyToken(p0: String?): Token {
        return DefaultToken("", 18000, "")
    }

    override fun allocateToken(p0: String?): Token {
        return DefaultToken("", 18000, "")
    }
}