package com.wang.sso.core.security.user

import com.wang.sso.modules.sys.entity.User
import com.wang.sso.modules.sys.utils.UserUtils
import org.springframework.beans.BeanUtils

/**
 * securityUser工厂
 * @author FlowersPlants
 * @since v1
 */
object SecurityUserFactory {
    /**
     * create security user by user.
     */
    fun create(user: User): SecurityUser {
        return SecurityUser().apply {
            BeanUtils.copyProperties(user, this)
            this.pwd = user.password
            this.roles = UserUtils.findRoleList(user.id)
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