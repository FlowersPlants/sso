package com.wang.sso.modules.sys.web

import com.wang.sso.common.dto.ResponseDto
import com.wang.sso.core.support.BaseController
import com.wang.sso.modules.sys.entity.Menu
import com.wang.sso.modules.sys.service.MenuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 用户菜单管理接口
 */
@RestController
@RequestMapping("/sys/menu")
class MenuController : BaseController() {
    @Autowired
    private lateinit var menuService: MenuService

    /**
     * 获取菜单树
     */
    @GetMapping("tree")
    fun tree(): Any? {
        return ResponseDto().apply {
            data = menuService.getMenuTree()
        }
    }

    /**
     * 根据角色ID获取该角色下的所有菜单
     */
    @GetMapping("{roleId}")
    fun getByRole(@PathVariable("roleId") roleId: String?): Any? {
        return ResponseDto().apply {
            data = menuService.getByRole(roleId)
        }
    }

    /**
     * 新增接口
     */
    @PostMapping
    fun insert(@RequestBody entity: Menu?): Any? {
        return ResponseDto().apply {
            data = menuService.save(entity)
        }
    }

    /**
     * 修改接口
     */
    @PutMapping
    fun update(@RequestBody entity: Menu?): Any? {
        return ResponseDto().apply {
            data = menuService.save(entity)
        }
    }

    /**
     * 删除接口（逻辑删除）
     */
    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") id: String?): Any? {
        return ResponseDto().apply {
            data = menuService.deleteById(id)
        }
    }
}