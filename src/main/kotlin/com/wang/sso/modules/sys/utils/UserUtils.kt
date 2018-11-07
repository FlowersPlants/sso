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
     * 获取当前登录信息
     */
    private fun getPrincipal(): Any? {
        val authentication = SecurityContextHolder.getContext().authentication
        return when (authentication) {
            null -> null
            else -> {
                when (authentication.principal) {
                    is UserDetails -> {
                        authentication.principal
                    }
                    else -> authentication.principal.toString()
                }
            }
        }
    }

    /**
     * 获取当前登录用户（SecurityUser）
     */
    fun getSecurityUser(): SecurityUser? {
        return if (getPrincipal() != null && getPrincipal() is UserDetails) {
            getPrincipal() as SecurityUser
        } else {
            null
        }
    }

    /**
     * 获取当前登录用户（User）
     */
    fun getCurrentUser(): User {
        return if (getSecurityUser() == null) {
            User()
        } else {
            SecurityUserFactory().create(getSecurityUser()!!)
        }
    }
}