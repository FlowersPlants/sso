package com.wang.sso.modules.sys.dao

import com.wang.sso.SsoApplicationTests
import com.wang.sso.core.config.WebSecurityConfig
import com.wang.sso.modules.sys.entity.User
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.Test

class UserDaoTest : SsoApplicationTests() {

    @Autowired
    private lateinit var userDao: IUserDao

    @Test
    fun insertTest() {
        val user = User("1")
        user.account = "admin"
        user.password = WebSecurityConfig().passwordEncoder().encode("admin")
        user.name = "wzj"
        userDao.insert(user)
    }

    @Test
    fun findListTest() {
        userDao.findList(User())
    }
}