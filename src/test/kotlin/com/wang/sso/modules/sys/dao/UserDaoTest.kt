package com.wang.sso.modules.sys.dao

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.wang.sso.SsoApplicationTests
import com.wang.sso.common.utils.JsonUtils
import com.wang.sso.core.security.config.WebSecurityConfig
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
        user.account = "flowers"
        user.password = WebSecurityConfig().passwordEncoder().encode("admin")
        user.name = "plants"
        userDao.insert(user)
    }

    @Test
    fun batchDeleteTest() {
        // 物理删除是还得把selectList也改为支持物理查询的接口，否则查不到结果便无法删除
        userDao.physicalDelete(User())
    }

    @Test
    fun findListTest() {
        println(JsonUtils.toJson(userDao.selectList(null)))
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