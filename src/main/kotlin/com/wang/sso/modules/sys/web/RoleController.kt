package com.wang.sso.modules.sys.web

import com.wang.sso.common.dto.ResponseDto
import com.wang.sso.common.utils.PaginationUtil
import com.wang.sso.core.support.BaseController
import com.wang.sso.modules.sys.dto.RoleDto
import com.wang.sso.modules.sys.entity.Role
import com.wang.sso.modules.sys.service.RoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 用户角色管理接口
 */
@RestController
@RequestMapping("/sys/role")
class RoleController : BaseController() {
    @Autowired
    private lateinit var roleService: RoleService

    /**
     * 用户角色管理分页
     */
    @GetMapping
    fun findPage(entity: Role?): Any? {
        return ResponseDto().apply {
            data = roleService.findPage(entity, PaginationUtil.getRequestPage(getRequest()))
        }
    }

    /**
     * 新增接口
     */
    @PostMapping
    fun insert(@RequestBody entity: Role?): Any? {
        return ResponseDto().apply {
            data = roleService.save(entity)
        }
    }

    /**
     * 角色授权接口
     */
    @PostMapping("auth")
    fun insertRecord(@RequestBody roleDto: RoleDto?): Any? {
        return ResponseDto().apply {
            data = roleService.insertBatchRecord(roleDto)
        }
    }

    /**
     * 修改接口
     */
    @PutMapping
    fun update(@RequestBody entity: Role?): Any? {
        return ResponseDto().apply {
            data = roleService.save(entity)
        }
    }

    /**
     * 删除接口（逻辑删除）
     */
    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") id: String?): Any? {
        return ResponseDto().apply {
            data = roleService.deleteById(id)
        }
    }
}