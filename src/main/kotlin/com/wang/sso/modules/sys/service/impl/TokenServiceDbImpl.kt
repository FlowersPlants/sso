package com.wang.sso.modules.sys.service.impl

import com.wang.sso.core.security.SecurityToken
import com.wang.sso.core.security.SecurityUser
import com.wang.sso.modules.sys.dao.ITokenDao
import com.wang.sso.modules.sys.service.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Service
@Primary
class TokenServiceDbImpl : TokenService {
    @Autowired
    private lateinit var tokenDao: ITokenDao

    override fun saveToken(securityUser: SecurityUser): SecurityToken {
        return tokenDao.saveToken(securityUser)
    }

    override fun refresh(securityUser: SecurityUser) {

    }

    override fun deleteToken(token: String): Boolean? {
        return false
    }
}