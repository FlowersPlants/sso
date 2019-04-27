package com.wang.sso.modules.sys.web

import com.wang.sso.common.utils.PaginationUtil
import com.wang.sso.core.support.BaseController
import com.wang.sso.modules.sys.entity.User
import com.wang.sso.modules.sys.service.MenuService
import com.wang.sso.modules.sys.service.UserService
import com.wang.sso.modules.sys.utils.UserUtils
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 用户管理相关接口
 * @author FlowersPlants
 * @since v1
 **/
@Api(tags = ["用户相关接口"])
@RestController
@RequestMapping("/sys/user")
open class UserController : BaseController() {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var menuService: MenuService

    /**
     * 获取用户信息
     */
    @ApiOperation("获取用户信息接口，用户登录时调用")
    @GetMapping("info")
    fun info(): Any? {
        val user = UserUtils.getCurrentUser()
        val currentUser = UserUtils.getCurrentUser()
        return mutableMapOf(
            "info" to user,
            "roles" to UserUtils.findRoleList(currentUser.id),
            "menus" to menuService.getUserMenuTree()
        )
    }

    /**
     * 分页接口
     */
    @ApiOperation("用户管理分页接口")
    @ApiImplicitParams(
        value = [
            ApiImplicitParam(name = "account", value = "用户账号", required = false),
            ApiImplicitParam(name = "name", value = "用户姓名", required = false),
            ApiImplicitParam(name = "current", value = "当前页", required = true),
            ApiImplicitParam(name = "pageSize", value = "每页数", required = true)
        ]
    )
    @GetMapping
    fun findPage(user: User?): Any? {
        return userService.findPage(user, PaginationUtil.getRequestPage(getRequest())!!)
    }

    /**
     * 新增接口
     */
    @ApiOperation("新增接口")
    @ApiImplicitParam(name = "user", value = "用户对象", required = true)
    @PostMapping
    fun insert(@RequestBody user: User?): Any? {
        return userService.save(user)
    }

    /**
     * 修改接口
     */
    @ApiOperation("修改接口")
    @ApiImplicitParam(name = "user", value = "用户对象", required = true)
    @PutMapping
    fun update(@RequestBody user: User?): Any? {
        return userService.save(user)
    }

    /**
     * 删除接口（逻辑删除）
     */
    @ApiOperation("逻辑删除接口")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true)
    @DeleteMapping
    fun delete(id: String?): Any? {
        return userService.deleteById(id)
    }
}
