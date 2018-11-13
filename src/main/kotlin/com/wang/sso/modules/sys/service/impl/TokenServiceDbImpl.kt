package com.wang.sso.modules.sys.service.impl

import com.baomidou.mybatisplus.core.toolkit.IdWorker
import com.wang.sso.core.security.user.SecurityToken
import com.wang.sso.core.security.user.SecurityUser
import com.wang.sso.modules.sys.dao.ITokenDao
import com.wang.sso.modules.sys.service.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 保存到db数据库的token
 *
 * @author FlowersPlants
 * @since v2
 */
@Service
class TokenServiceDbImpl : TokenService {
    @Autowired
    private lateinit var tokenDao: ITokenDao

    override fun saveToken(securityUser: SecurityUser): SecurityToken {
        securityUser.token=IdWorker.get32UUID()
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