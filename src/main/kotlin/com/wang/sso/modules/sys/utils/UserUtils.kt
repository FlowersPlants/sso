package com.wang.sso.modules.sys.utils

import com.wang.sso.common.utils.JsonUtils
import com.wang.sso.core.security.user.SecurityUser
import com.wang.sso.core.security.user.SecurityUserFactory
import com.wang.sso.modules.sys.entity.Role
import com.wang.sso.modules.sys.entity.User
import com.wang.sso.modules.sys.vo.MenuTree
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

/**
 * user相关工具类
 * @author FlowersPlants
 * @since v1
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
        return if (!Objects.isNull(principal)) {
            when (principal) {
                is UserDetails -> principal as SecurityUser
                is String -> JsonUtils.readValue(principal, SecurityUser::class.java)// 鉴权时此处被调用
                else -> SecurityUser()
            }
        } else {
            SecurityUser()
        }
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

    /**
     * 当前用户的所有角色，从session获取？
     */
    fun getCurrentUserRoles(): MutableList<Role>? {
        TODO("not impl")
    }

    /**
     * 当前用户的所有菜单
     */
    fun getCurrentUserMenuTree(): MutableList<MenuTree>? {
        TODO("not impl")
    }
}