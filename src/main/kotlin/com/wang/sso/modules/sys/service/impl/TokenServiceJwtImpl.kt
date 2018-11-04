package com.wang.sso.modules.sys.service.impl

import com.wang.sso.core.security.SecurityToken
import com.wang.sso.core.security.SecurityUser
import com.wang.sso.modules.sys.service.TokenService
import org.springframework.stereotype.Service

/**
 * jwt方式token
 */
@Service
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