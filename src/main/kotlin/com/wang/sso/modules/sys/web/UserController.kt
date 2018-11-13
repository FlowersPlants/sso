package com.wang.sso.modules.sys.web

import com.wang.sso.common.dto.ResponseDto
import com.wang.sso.common.utils.PaginationUtil
import com.wang.sso.core.support.BaseController
import com.wang.sso.modules.sys.entity.User
import com.wang.sso.modules.sys.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 用户管理相关接口
 * 在 @PreAuthorize 中我们可以利用内建的 SPEL 表达式：比如 'hasRole()' 来决定哪些用户有权访问。
 * 需注意的一点是 hasRole 表达式认为每个角色名字前都有一个前缀 'ROLE_'。所以这里的 'ADMIN' 其实在
 * 数据库中存储的是 'ROLE_ADMIN' 。这个 @PreAuthorize 可以修饰Controller也可修饰Controller中的方法。
 *
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
//    @PreAuthorize("hasAnyRole('ADMIN,USER')")
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
     * 新增接口
     */
    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    fun insert(@RequestBody user: User): ResponseEntity<*> {
        return ResponseEntity.ok(ResponseDto().apply {
            data = userService.insert(user)
        })
    }

    /**
     * 修改接口
     */
    @PutMapping
//    @PreAuthorize("hasRole('ADMIN')")
    fun update(@RequestBody user: User): ResponseEntity<*> {
        return ResponseEntity.ok(ResponseDto().apply {
            data = userService.update(user)
        })
    }

    /**
     * 删除接口
     */
    @DeleteMapping
//    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@RequestBody user: User): ResponseEntity<*> {
        return ResponseEntity.ok(ResponseDto().apply {
            data = userService.delete(user)
        })
    }
}