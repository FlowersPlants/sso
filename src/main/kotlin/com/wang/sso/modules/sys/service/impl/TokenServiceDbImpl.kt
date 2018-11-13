package com.wang.sso.modules.sys.service.impl

import com.wang.sso.common.idgen.IdGenerate
import com.wang.sso.core.security.user.SecurityToken
import com.wang.sso.core.security.user.SecurityUser
import com.wang.sso.modules.sys.dao.ITokenDao
import com.wang.sso.modules.sys.service.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 保存到db数据库的token
 */
@Service
class TokenServiceDbImpl : TokenService {
    @Autowired
    private lateinit var tokenDao: ITokenDao

    override fun saveToken(securityUser: SecurityUser): SecurityToken {
        securityUser.token=IdGenerate.uuid()
        return tokenDao.saveToken(securityUser).apply {
            this.token = securityUser.token
        }
    }

    override fun refresh(securityUser: SecurityUser) {

    }

    override fun deleteToken(token: String): Boolean? {
        return false
    }
}