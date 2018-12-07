package com.wang.sso.modules.sys.web

import com.wang.sso.common.dto.ResponseDto
import com.wang.sso.common.utils.PaginationUtil
import com.wang.sso.core.support.BaseController
import com.wang.sso.modules.sys.entity.User
import com.wang.sso.modules.sys.service.MenuService
import com.wang.sso.modules.sys.service.UserService
import com.wang.sso.modules.sys.utils.UserUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 用户管理相关接口
 * @author FlowersPlants
 * @since v1
 **/
@RestController
@RequestMapping("/sys/user")
class UserController : BaseController() {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var menuService: MenuService

    /**
     * 获取用户信息
     */
    @GetMapping("info")
    fun info(): ResponseEntity<*> {
        val user = UserUtils.getCurrentUser()
        return ResponseEntity.ok(ResponseDto().apply {
            data = mutableMapOf(
                "info" to user,
                "roles" to UserUtils.findRoleList(),
                "menus" to menuService.getUserMenuTree()
            )
        })
    }

    /**
     * 分页接口
     */
    @GetMapping
    fun findPage(user: User): ResponseEntity<*> {
        return ResponseEntity.ok(ResponseDto().apply {
            data = userService.findPage(user, PaginationUtil.getRequestPage(getRequest())!!)
        })
    }

    /**
     * 新增接口
     */
    @PostMapping
    fun insert(@RequestBody user: User): ResponseEntity<*> {
        return ResponseEntity.ok(ResponseDto().apply {
            data = userService.insert(user)
        })
    }

    /**
     * 修改接口
     */
    @PutMapping
    fun update(@RequestBody user: User): ResponseEntity<*> {
        return ResponseEntity.ok(ResponseDto().apply {
            data = userService.update(user)
        })
    }

    /**
     * 删除接口（逻辑删除）
     */
    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") id: String?): ResponseEntity<*> {
        return ResponseEntity.ok(ResponseDto().apply {
            data = userService.deleteById(id)
        })
    }
}