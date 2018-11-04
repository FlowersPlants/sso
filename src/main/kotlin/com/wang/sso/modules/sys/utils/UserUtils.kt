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
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication != null && authentication.principal is UserDetails) {
            authentication.principal as SecurityUser
        } else {
            SecurityUser()
        }
    }

    /**
     * 获取当前登录用户，有点问题，需要修改哦！！！
     */
    fun getCurrentUser() :User {
        return SecurityUserFactory.create(getSecurityUser())
    }
}