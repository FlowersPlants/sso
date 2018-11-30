package com.wang.sso.modules.sys.web

import com.wang.sso.common.dto.ResponseDto
import com.wang.sso.common.utils.PaginationUtil
import com.wang.sso.core.support.BaseController
import com.wang.sso.modules.sys.entity.Menu
import com.wang.sso.modules.sys.entity.Role
import com.wang.sso.modules.sys.entity.User
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

    /**
     * 获取列表接口
     */
    @GetMapping
    fun list(user: User): ResponseEntity<*> {
        return ResponseEntity.ok(ResponseDto().apply {
            data = userService.findList(user)
        })
    }

    /**
     * 分页接口
     */
    @GetMapping("page")
    fun page(user: User): ResponseEntity<*> {
        return ResponseEntity.ok(ResponseDto().apply {
            data = userService.findPage(user, PaginationUtil.getRequestPage(getRequest())!!)
        })
    }

    /**
     * 获取用户信息
     */
    @GetMapping("info")
    fun info(): ResponseEntity<*> {
        val user = UserUtils.getCurrentUser()
        return ResponseEntity.ok(ResponseDto().apply {
            data = mutableMapOf(
                "info" to user,
//                "menus" to menuService.getUserMenuTree(),
//                "roles" to UserUtils.getCurrentUserRoles()
                "menus" to mutableListOf<Menu>(),
                "roles" to mutableListOf<Role>()
            )
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
    @DeleteMapping
    fun delete(@RequestBody user: User): ResponseEntity<*> {
        return ResponseEntity.ok(ResponseDto().apply {
            data = userService.delete(user)
        })
    }
}