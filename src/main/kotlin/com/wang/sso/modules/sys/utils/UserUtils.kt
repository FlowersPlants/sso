package com.wang.sso.modules.sys.utils

import com.wang.sso.common.utils.SpringUtils
import com.wang.sso.core.security.user.SecurityUtils
import com.wang.sso.modules.sys.dao.IRoleDao
import com.wang.sso.modules.sys.dao.IUserDao
import com.wang.sso.modules.sys.entity.Role
import com.wang.sso.modules.sys.entity.User

/**
 * user相关工具类
 * @author FlowersPlants
 * @since v1
 */
object UserUtils {
    private val userDao = SpringUtils.getBean(IUserDao::class.java)
    private val roleDao = SpringUtils.getBean(IRoleDao::class.java)

    /**
     * 获取当前登录用户（User）
     */
    fun getCurrentUser(): User {
        val securityUser = SecurityUtils.getSecurityUser()
        return if (securityUser == null) {
            User()
        } else {
            findUserByAccount(securityUser.account) ?: User()
        }
    }

    /**
     * 获取用户信息，后期先从缓存获取，没有再从数据库获取
     * @param account 用户账号
     */
    fun findUserByAccount(account: String?): User? {
        if (account == null || account.isNullOrEmpty()) {
            return null
        }
        return userDao.findUserByAccount(account)
    }

    /**
     * 根据用户ID获取角色信息，后期先从缓存获取，没有则从数据库
     */
    fun findRolesByUserId(userId: String?): MutableList<Role> {
        if (userId == null || userId.isNullOrEmpty()) {
            return mutableListOf()
        }
        val roleList = roleDao.findByUserId(userId)
        return if (roleList.isEmpty()) {
            mutableListOf()
        } else {
            roleList
        }
    }
}