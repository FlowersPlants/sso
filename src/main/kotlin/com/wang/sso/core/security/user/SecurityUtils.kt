package com.wang.sso.core.security.user

import com.wang.sso.common.utils.JsonUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

/**
 * 认证相关工具类
 */
object SecurityUtils {
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
        return if (Objects.nonNull(principal)) {
            when (principal) {
                is UserDetails -> principal as SecurityUser
                is String -> JsonUtils.readValue(principal, SecurityUser::class.java)// 鉴权时此处被调用
                else -> null
            }
        } else {
            null
        }
    }
}