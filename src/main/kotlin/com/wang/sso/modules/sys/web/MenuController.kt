package com.wang.sso.modules.sys.web

import com.wang.sso.core.support.BaseController
import com.wang.sso.modules.sys.entity.Menu
import com.wang.sso.modules.sys.service.MenuService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 用户菜单管理接口
 */
@Api(tags = ["系统菜单相关接口"])
@RestController
@RequestMapping("/sys/menu")
class MenuController : BaseController() {
    @Autowired
    private lateinit var menuService: MenuService

    /**
     * 获取菜单树
     */
    @ApiOperation("获取菜单树接口")
    @GetMapping("tree")
    fun tree(): Any? {
        return menuService.getMenuTree()
    }

    /**
     * 根据角色ID获取该角色下的所有菜单
     */
    @ApiOperation("根据角色ID获取所有菜单列表接口")
    @ApiImplicitParam(name = "roleId", value = "角色ID", required = true)
    @GetMapping
    fun getByRole(roleId: String?): Any? {
        return menuService.getByRole(roleId)
    }

    /**
     * 新增接口
     */
    @ApiOperation("菜单管理新增接口")
    @ApiImplicitParam(name = "menu", value = "系统菜单对象", required = true)
    @PostMapping
    fun insert(@RequestBody entity: Menu?): Any? {
        return menuService.save(entity)
    }

    /**
     * 修改接口
     */
    @ApiOperation("字典管理分页接口")
    @ApiImplicitParam(name = "menu", value = "系统菜单对象", required = true)
    @PutMapping
    fun update(@RequestBody entity: Menu?): Any? {
        return menuService.save(entity)
    }

    /**
     * 删除接口（逻辑删除）
     */
    @ApiOperation("系统菜单逻辑删除接口")
    @ApiImplicitParam(name = "id", value = "菜单ID", required = true)
    @DeleteMapping
    fun delete(id: String?): Any? {
        return menuService.deleteById(id)
    }
}
