package com.wang.sso.modules.sys.web

import com.wang.sso.modules.sys.entity.User
import com.wang.sso.modules.sys.service.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * 权限认证
 * @author FlowersPlants
 * @since v1
 */
@RestController
@Api(tags = ["认证相关接口"])
class AuthController {

    @Autowired
    private lateinit var userService: UserService

    /**
     * 注册接口
     */
    @ApiOperation("注册接口")
    @ApiImplicitParam(name = "user", value = "用户信息，账户名、密码等", required = true, dataType = "User")
    @PostMapping("/auth/register")
    fun register(@RequestBody user: User): Any? {
        return userService.save(user)
    }
}
