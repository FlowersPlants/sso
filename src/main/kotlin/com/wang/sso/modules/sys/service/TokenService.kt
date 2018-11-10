package com.wang.sso.modules.sys.service

import com.wang.sso.core.security.base.SecurityToken
import com.wang.sso.core.security.base.SecurityUser

/**
 * token管理器
 * 可保存到数据库或者redis，具体看实现类
 * 注解@see Primary表示默认的实现类
 */
interface TokenService{
    fun saveToken(securityUser: SecurityUser): SecurityToken

    fun refresh(securityUser: SecurityUser)

    fun deleteToken(token: String): Boolean?
}