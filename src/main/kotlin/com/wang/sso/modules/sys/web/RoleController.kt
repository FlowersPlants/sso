package com.wang.sso.modules.sys.web

import com.wang.sso.common.utils.PaginationUtil
import com.wang.sso.core.support.BaseController
import com.wang.sso.modules.sys.dto.RoleDto
import com.wang.sso.modules.sys.entity.Role
import com.wang.sso.modules.sys.service.RoleService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 用户角色管理接口
 */
@Api(tags = ["角色管理相关接口"])
@RestController
@RequestMapping("/sys/role")
class RoleController : BaseController() {
    @Autowired
    private lateinit var roleService: RoleService

    /**
     * 查看详情
     */
    @ApiOperation("查看详情接口")
    @ApiImplicitParam(name = "id", value = "角色ID", required = true)
    @GetMapping("detail")
    fun findById(id: String?): Any? {
        return roleService.findById(id)
    }

    /**
     * 用户角色管理分页
     */
    @ApiOperation("分页接口")
    @ApiImplicitParams(
        value = [
            ApiImplicitParam(name = "name", value = "角色名称", required = false),
            ApiImplicitParam(name = "enname", value = "角色英文名称", required = false),
            ApiImplicitParam(name = "current", value = "当前页", required = true),
            ApiImplicitParam(name = "pageSize", value = "每页数", required = true)
        ]
    )
    @GetMapping
    fun findPage(entity: Role?): Any? {
        return roleService.findPage(entity, PaginationUtil.getRequestPage(getRequest())!!)
    }

    /**
     * 新增接口
     */
    @ApiOperation("新增接口")
    @ApiImplicitParam(name = "role", value = "角色对象", required = true)
    @PostMapping
    fun insert(@RequestBody entity: Role?): Any? {
        return roleService.save(entity)
    }

    /**
     * 角色授权接口
     */
    @ApiOperation("角色授权接口")
    @ApiImplicitParams(
        value = [
            ApiImplicitParam(name = "id", value = "角色ID", required = true),
            ApiImplicitParam(name = "menuIds", value = "菜单ID集合", required = true)
        ]
    )
    @PostMapping("auth")
    fun roleAuth(@RequestBody roleDto: RoleDto?): Any? {
        return roleService.insertBatchRecord(roleDto)
    }

    /**
     * 角色用户分配接口
     */
    @ApiOperation("角色用户分配接口")
    @ApiImplicitParams(
        value = [
            ApiImplicitParam(name = "id", value = "角色ID", required = true),
            ApiImplicitParam(name = "userIds", value = "用户ID集合", required = true)
        ]
    )
    @PostMapping("assign")
    fun assignUsers(@RequestBody roleDto: RoleDto?): Any? {
        return roleService.insertBatchRecord(roleDto)
    }

    /**
     * 修改接口
     */
    @ApiOperation("修改接口")
    @ApiImplicitParam(name = "role", value = "角色对象", required = true)
    @PutMapping
    fun update(@RequestBody entity: Role?): Any? {
        return roleService.save(entity)
    }

    /**
     * 删除接口（逻辑删除）
     */
    @ApiOperation("逻辑删除接口")
    @ApiImplicitParam(name = "id", value = "角色ID", required = true)
    @DeleteMapping
    fun delete(id: String?): Any? {
        return roleService.deleteById(id)
    }
}
