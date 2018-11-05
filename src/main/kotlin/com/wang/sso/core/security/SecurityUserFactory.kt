package com.wang.sso.core.security

import com.wang.sso.common.idgen.IdGenerate
import com.wang.sso.common.utils.SpringUtils
import com.wang.sso.modules.sys.dao.IMenuDao
import com.wang.sso.modules.sys.dao.IRoleDao
import com.wang.sso.modules.sys.entity.Menu
import com.wang.sso.modules.sys.entity.User
import org.springframework.beans.BeanUtils
import java.util.*

/**
 * securityUser工厂
 */
object SecurityUserFactory {
    private val menuDao = SpringUtils.getBean(IMenuDao::class.java)
    private val roleDao = SpringUtils.getBean(IRoleDao::class.java)

    /**
     * create security user by user.
     */
    fun create(user: User): SecurityUser {
        return SecurityUser().apply {
            BeanUtils.copyProperties(user, this)
            this.account = user.account
            this.pwd = user.password
            this.token = IdGenerate.uuid() // token暂时使用uuid
            this.loginTime = Date().time
            this.expireTime = 18000L // 超时时间可从配置文件获取
            this.roles = roleDao.findByUserId(user.id)
            this.menus = getListByUser(user)
        }
    }

    /**
     * 根据userID获取其所有权限，并去重
     */
    private fun getListByUser(user: User): MutableList<Menu> {
        var menuList = menuDao.findListByUserId(user.id)
        // 过滤
        menuList = ArrayList(LinkedHashSet<Menu>(menuList))
        return menuList
    }
}