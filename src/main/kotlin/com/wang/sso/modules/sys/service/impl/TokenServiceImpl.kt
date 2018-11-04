package com.wang.sso.modules.sys.service.impl

import org.springframework.security.core.token.DefaultToken
import org.springframework.security.core.token.Token
import org.springframework.security.core.token.TokenService
import org.springframework.stereotype.Service

/**
 * 一般的token，spring框架的token
 */
@Service
class TokenServiceImpl : TokenService {
    override fun verifyToken(p0: String?): Token {
        return DefaultToken("", 18000, "")
    }

    override fun allocateToken(p0: String?): Token {
        return DefaultToken("", 18000, "")
    }
}