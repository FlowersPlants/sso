package com.wang.sso.modules.sys.service

import com.wang.sso.core.security.user.SecurityToken
import com.wang.sso.core.security.user.SecurityUser

/**
 * token管理器
 * 可保存到数据库或者redis，具体看实现类
 * 注解@see Primary表示默认的实现类
 *
 * @author FlowersPlants
 * @since v2
 */
interface TokenService{
    fun saveToken(securityUser: SecurityUser): SecurityToken

    fun refresh(securityUser: SecurityUser)

    fun deleteToken(token: String): Boolean?
}