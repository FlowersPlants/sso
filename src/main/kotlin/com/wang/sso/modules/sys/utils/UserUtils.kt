package com.wang.sso.modules.sys.utils

import com.wang.sso.common.utils.JsonUtils
import com.wang.sso.core.security.user.SecurityUser
import com.wang.sso.core.security.user.SecurityUserFactory
import com.wang.sso.modules.sys.entity.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

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
            else -> authentication.principal
        }
    }

    /**
     * 获取当前登录用户（SecurityUser）
     */
    fun getSecurityUser(): SecurityUser? {
        val principal = getPrincipal()
        when (Objects.isNull(principal)) {
            false -> {
                when (principal) {
                    is UserDetails -> principal as SecurityUser
                    is String -> JsonUtils.readValue(principal, SecurityUser::class.java)// 鉴权时此处被调用
                }
            }
        }
        return null
    }

    /**
     * 获取当前登录用户（User）
     */
    fun getCurrentUser(): User {
        return if (getSecurityUser() == null) {
            User()
        } else {
            SecurityUserFactory.create(getSecurityUser() ?: SecurityUser())
        }
    }
}