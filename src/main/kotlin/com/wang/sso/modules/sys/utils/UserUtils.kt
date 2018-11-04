package com.wang.sso.modules.sys.utils

import com.wang.sso.core.security.SecurityUser
import com.wang.sso.core.security.SecurityUserFactory
import com.wang.sso.modules.sys.entity.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

/**
 * user相关工具类
 */
object UserUtils {

    /**
     * 获取当前登录用户（SecurityUser）
     */
    fun getSecurityUser(): SecurityUser {
        val principal = SecurityContextHolder.getContext().authentication.principal
        return if (principal is UserDetails) {
            principal as SecurityUser
        } else {
            SecurityUser()
        }
    }

    /**
     * 获取当前登录用户
     */
    fun getCurrentUser() :User {
        return SecurityUserFactory.create(getSecurityUser())
    }
}