package com.wang.sso.modules.sys.utils

import com.wang.sso.common.utils.SpringUtils
import com.wang.sso.core.cache.redis.RedisService
import com.wang.sso.core.consts.CommonConstant
import com.wang.sso.core.security.user.SecurityUtils
import com.wang.sso.modules.sys.dao.IMenuDao
import com.wang.sso.modules.sys.dao.IRoleDao
import com.wang.sso.modules.sys.dao.IUserDao
import com.wang.sso.modules.sys.entity.Menu
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
    private val menuDao = SpringUtils.getBean(IMenuDao::class.java)

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
        var user = redisService.hget(CommonConstant.CACHE_USERS, CommonConstant.USER_CACHE_ID + id) as? User
        if (user == null) {
            user = userDao.selectById(id)
            if (user == null) {
                return null
            }
            redisService.hset(CommonConstant.CACHE_USERS, CommonConstant.USER_CACHE_ID + user.id, user)
            redisService.hset(CommonConstant.CACHE_USERS, CommonConstant.USER_CACHE_ACCOUNT + user.account, user)
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
            redisService.hget(CommonConstant.CACHE_USERS, CommonConstant.USER_CACHE_ACCOUNT + account) as? User
        if (user == null) {
            user = userDao.findUserByAccount(account)
            if (user == null) {
                return null
            }
            redisService.hset(CommonConstant.CACHE_USERS, CommonConstant.USER_CACHE_ID + user.id, user)
            redisService.hset(CommonConstant.CACHE_USERS, CommonConstant.USER_CACHE_ACCOUNT + user.account, user)
        }
        return user
    }

    /**
     * 根据用户ID获取角色信息；先从缓存获取，没有则从数据库
     */
    @Suppress("UNCHECKED_CAST")
    fun findRolesByUserId(userId: String?): MutableList<Role>? {
        if (userId == null || userId.isNullOrEmpty()) {
            return null
        }
        var list = redisService.hget(CommonConstant.CACHE_USER_ROLES, userId) as? MutableList<Role>
        if (list == null) {
            list = roleDao.findByUserId(userId)
            if (list == null || list.isEmpty()) {
                return null
            }
            redisService.hset(CommonConstant.CACHE_USER_ROLES, userId, list)
        }
        return list
    }

    /**
     * 获取当前用户所有角色
     */
    fun findRoleList(): MutableList<Role>? {
        return findRolesByUserId(getCurrentUser().id)
    }

    /**
     * 根据用户ID获取其所有菜单信息
     * 先从缓存获取，没有则从数据库查询，从数据库获取的数据可能需要去重
     */
    @Suppress("UNCHECKED_CAST")
    fun findMenusByUserId(userId: String?): MutableList<Menu>? {
        if (userId == null || userId.isNullOrEmpty()) {
            return null
        }
        var list = redisService.hget(CommonConstant.CACHE_USER_MENUS, userId) as? MutableList<Menu>
        if (list == null) {
            val roleList = findRolesByUserId(userId) ?: mutableListOf()
            val roleIds = roleList.map { it.id }
            if (roleIds.isEmpty()) {
                return null
            }
            list = menuDao.findByRoleIds(roleIds)
            if (list == null || list.isEmpty()) {
                return null
            }

            // 去重，待测试
            // list.filter { list.contains(it) }

            // 把去重后的结果放在缓存
            redisService.hset(CommonConstant.CACHE_USER_MENUS, userId, list)
        }
        return list
    }

    /**
     * 获取当前用户的所有菜单
     */
    fun findMenuList(): MutableList<Menu>? {
        return findMenusByUserId(getCurrentUser().id)
    }

    /**
     * 清除当前用户缓存
     */
    fun clearCache() {
        redisService.del(CommonConstant.CACHE_USER_ROLES, CommonConstant.CACHE_USER_MENUS)
        clearCache(getCurrentUser())
    }

    /**
     * 清除指定用户缓存
     */
    fun clearCache(user: User) {
        redisService.hdel(
            CommonConstant.CACHE_USERS,
            CommonConstant.USER_CACHE_ACCOUNT + user.account,
            CommonConstant.USER_CACHE_ID + user.id
        )
    }
}