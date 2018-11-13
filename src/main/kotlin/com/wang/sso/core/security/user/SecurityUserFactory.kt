package com.wang.sso.core.security.user

import com.wang.sso.common.utils.SpringUtils
import com.wang.sso.modules.sys.dao.IRoleDao
import com.wang.sso.modules.sys.entity.User
import org.springframework.beans.BeanUtils

/**
 * securityUser工厂
 */
object SecurityUserFactory {
    private val roleDao = SpringUtils.getBean(IRoleDao::class.java)

    /**
     * create security user by user.
     */
    fun create(user: User): SecurityUser {
        return SecurityUser().apply {
            BeanUtils.copyProperties(user, this)
            this.pwd = user.password
            this.roles = roleDao.findByUserId(user.id)
        }
    }

    /**
     * create user by security user
     */
    fun create(securityUser: SecurityUser): User {
        return User().apply {
            BeanUtils.copyProperties(securityUser, this)
        }
    }
}