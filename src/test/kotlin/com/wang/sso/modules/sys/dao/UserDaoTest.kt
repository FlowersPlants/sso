package com.wang.sso.modules.sys.dao

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.wang.sso.SsoApplicationTests
import com.wang.sso.core.config.WebSecurityConfig
import com.wang.sso.modules.sys.entity.User
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.Test

/**
 * user相关测试，OK
 */
class UserDaoTest : SsoApplicationTests() {
    @Autowired
    private lateinit var userDao: IUserDao

    @Test
    fun insertTest() {
        val user = User()
        user.account = "admin"
        user.password = WebSecurityConfig().passwordEncoder().encode("admin")
        user.name = "wzj"
        userDao.insert(user)
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