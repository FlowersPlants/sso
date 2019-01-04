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
 * 缓存部分还有一些问题
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
     * 用户是否超级管理员
     * @param userId 用户ID
     */
    private fun isAdmin(userId: String?): Boolean {
        return userId == CommonConstant.DEFAULT_ADMIN_ID
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
        var user = redisService.hget(CommonConstant.CACHE_USERS, CommonConstant.USER_CACHE_ACCOUNT + account) as? User
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


    // ----------------------------- role --------------------------------
    /**
     * 根据用户ID获取角色信息；先从缓存获取，没有则从数据库
     */
    @Suppress("UNCHECKED_CAST")
    fun findRolesByUserId(userId: String): MutableList<Role>? {
        var list = redisService.get(CommonConstant.CACHE_ROLES) as? MutableList<Role>
        if (list == null) {
            list = if (isAdmin(userId)) {
                roleDao.selectList(null)
            } else {
                roleDao.findByUserId(userId)
            }
            if (list == null || list.isEmpty()) {
                return null
            }

            redisService.set(CommonConstant.CACHE_ROLES, list)
        }
        return list
    }

    /**
     * 获取所有角色列表
     */
    @Suppress("UNCHECKED_CAST")
    fun findAllRole(): MutableList<Role>? {
        var list = redisService.get(CommonConstant.CACHE_ROLES) as? MutableList<Role>
        if (list == null) {
            list = roleDao.selectList(null)
            if (list == null) {
                return null
            }
            redisService.set(CommonConstant.CACHE_MENUS, list)
        }
        return list
    }

    /**
     * 获取当前用户所有角色
     */
    fun findRoleList(userId: String?): MutableList<Role>? {
        return if (userId == null || userId.isNullOrEmpty()) {
            findAllRole()
        } else {
            findRolesByUserId(userId)
        }
    }


    // -------------------- menu -----------------------------
    /**
     * 根据用户ID获取其所有菜单信息
     * 先从缓存获取，没有则从数据库查询，从数据库获取的数据可能需要去重
     */
    @Suppress("UNCHECKED_CAST")
    fun findMenusByUserId(userId: String): MutableList<Menu>? {
        var list = redisService.get(CommonConstant.CACHE_MENUS) as? MutableList<Menu>
        if (list == null) {
            list = if (isAdmin(userId)) {
                menuDao.selectList(null)
            } else {
                val roleList = findRolesByUserId(userId) ?: mutableListOf()
                val roleIds = roleList.map { it.id }
                if (roleIds.isEmpty()) {
                    return null
                }
                menuDao.findByRoleIds(roleIds)
            }
            if (list == null || list.isEmpty()) {
                return null
            }

            // 把去重后的结果放在缓存
            redisService.set(CommonConstant.CACHE_MENUS, list)
        }
        return list
    }

    /**
     * 获取所有菜单
     */
    @Suppress("UNCHECKED_CAST")
    fun findAllMenu(): MutableList<Menu>? {
        var list = redisService.get(CommonConstant.CACHE_MENUS) as? MutableList<Menu>
        if (list == null) {
            list = menuDao.selectList(null)
            if (list == null) {
                return null
            }
            redisService.set(CommonConstant.CACHE_MENUS, list)
        }
        return list
    }

    /**
     * 获取菜单
     * @param userId 用户ID，如果ID不为null则获取该用户菜单，否则获取所有菜单
     */
    fun findMenuList(userId: String?): MutableList<Menu>? {
        return if (userId == null || userId.isNullOrEmpty()) {
            findAllMenu()
        } else {
            findMenusByUserId(userId)
        }
    }


    // ---------------------------- cache ------------------------------
    /**
     * 清除当前用户缓存
     */
    fun clearCache() {
        redisService.del(CommonConstant.CACHE_ROLES, CommonConstant.CACHE_MENUS)
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
