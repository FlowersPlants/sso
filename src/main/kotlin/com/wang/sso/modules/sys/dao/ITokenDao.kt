package com.wang.sso.modules.sys.dao

import com.wang.sso.core.annotations.MyBatisDao
import com.wang.sso.core.security.user.SecurityToken
import com.wang.sso.core.security.user.SecurityUser
import com.wang.sso.core.support.BaseDao
import com.wang.sso.modules.sys.entity.Token

/**
 * 保存到数据库的token
 * @author FlowersPlants
 * @since v2
 */
@MyBatisDao(value = "tokenDao")
interface ITokenDao : BaseDao<Token> {

    /**
     * 保存token到数据库
     */
    fun saveToken(securityUser: SecurityUser): SecurityToken
}