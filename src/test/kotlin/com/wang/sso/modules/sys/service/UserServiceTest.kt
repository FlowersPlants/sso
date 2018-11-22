package com.wang.sso.modules.sys.service

import com.wang.sso.SsoApplicationTests
import com.wang.sso.common.utils.JsonUtils
import com.wang.sso.modules.sys.entity.User
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.Test

/**
 * redis cache测试
 * @author FlowersPlants
 * @since v1
 */
open class UserServiceTest : SsoApplicationTests() {
    @Autowired
    private lateinit var userService: UserService

    @Test
    fun findList(){
        println(JsonUtils.toJson(userService.findList(User())))
    }
}