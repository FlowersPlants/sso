package com.wang.sso.modules.sys.dao

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.wang.sso.SsoApplicationTests
import com.wang.sso.core.config.WebSecurityConfig
import com.wang.sso.modules.sys.entity.User
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.Test

/**
 * user相关测试，OK
 * @author FlowersPlants
 * @since v1
 */
class UserDaoTest : SsoApplicationTests() {
    @Autowired
    private lateinit var userDao: IUserDao

    @Test
    fun insertTest() {
        val user = User()
        user.account = "system"
        user.password = WebSecurityConfig().passwordEncoder().encode("admin")
        user.name = "系统管理员"
        userDao.insert(user)
    }

    @Test
    fun batchDeleteTest() {
        userDao.selectList(QueryWrapper<User>().apply {
            eq("account", "system")
        }).forEach {
            userDao.deleteById(it.id)
        }
    }

    @Test
    fun findListTest() {
        userDao.selectList(null)
    }

    @Test
    fun findPageTest() {
        val page = Page<User>().apply {
            this.current = 1
            size = 10
        }
        userDao.selectPage(page, null)
    }
}