package com.wang.sso.modules.sys.utils

import com.wang.sso.common.utils.SpringUtils
import com.wang.sso.core.cache.redis.RedisService
import com.wang.sso.core.consts.CommonConstant
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

    // redis操作接口
    private val redisService = SpringUtils.getBean(RedisService::class.java)

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
     * 根据ID获取用户信息；先从缓存获取，没有再从数据库获取
     * 在进行用户更新操作时重新设置此缓存
     * @param id 用户ID
     */
    fun findUserById(id: String?): User? {
        if (id == null || id.isNullOrEmpty()) {
            return null
        }
        var user = redisService.getHashValue(CommonConstant.USER_CACHE, CommonConstant.USER_CACHE_ID + id) as? User
        if (user == null) {
            user = userDao.selectById(id)
            if (user == null) {
                return null
            }
            redisService.setHashMap(CommonConstant.USER_CACHE, CommonConstant.USER_CACHE_ID + user.id, user)
            redisService.setHashMap(CommonConstant.USER_CACHE, CommonConstant.USER_CACHE_ACCOUNT + user.account, user)
        }
        return user
    }

    /**
     * 根据用户名获取用户信息；先从缓存获取，没有再从数据库获取
     * 在进行用户更新操作时重新设置此缓存
     * @param account 用户账号
     */
    fun findUserByAccount(account: String?): User? {
        if (account == null || account.isNullOrEmpty()) {
            return null
        }
        var user =
            redisService.getHashValue(CommonConstant.USER_CACHE, CommonConstant.USER_CACHE_ACCOUNT + account) as? User
        if (user == null) {
            user = userDao.findUserByAccount(account)
            if (user == null) {
                return null
            }
            redisService.setHashMap(CommonConstant.USER_CACHE, CommonConstant.USER_CACHE_ID + user.id, user)
            redisService.setHashMap(CommonConstant.USER_CACHE, CommonConstant.USER_CACHE_ACCOUNT + user.account, user)
        }
        return user
    }

    /**
     * 根据用户ID获取角色信息；先从缓存获取，没有则从数据库
     * 可能有问题（hash还是普通字符串？），待修改
     */
    @Suppress("UNCHECKED_CAST")
    fun findRolesByUserId(userId: String?): MutableList<Role> {
        if (userId == null || userId.isNullOrEmpty()) {
            return mutableListOf()
        }
        var list = redisService.get(CommonConstant.CACHE_ROLE_LIST) as? MutableList<Role>
        if (list == null) {
            list = roleDao.findByUserId(userId)
            if (list.isEmpty()) {
                return mutableListOf()
            }
            redisService.set(CommonConstant.CACHE_ROLE_LIST, list)
        }
        return list
    }

    /**
     * 清除当前用户缓存
     */
    fun clearCache() {
        redisService.del(CommonConstant.CACHE_ROLE_LIST)
        redisService.del(CommonConstant.CACHE_MENU_LIST)
        clearCache(getCurrentUser())
    }

    /**
     * 清除指定用户缓存
     */
    fun clearCache(user: User) {
        redisService.delHashMap(CommonConstant.USER_CACHE, "USER_CACHE_ACCOUNT${user.account}, USER_CACHE_ID${user.id}")
    }
}