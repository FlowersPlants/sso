package com.wang.sso.modules.sys.web

import com.wang.sso.common.dto.ResponseDto
import com.wang.sso.core.support.BaseController
import com.wang.sso.modules.sys.entity.Menu
import com.wang.sso.modules.sys.service.MenuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
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
    fun tree(entity: Menu): ResponseEntity<*> {
        return ResponseEntity.ok(ResponseDto().apply {
            data = menuService.getMenuTree()
        })
    }

    /**
     * 新增接口
     */
    @PostMapping
    fun insert(@RequestBody entity: Menu): ResponseEntity<*> {
        return ResponseEntity.ok(ResponseDto().apply {
            data = menuService.insert(entity)
        })
    }

    /**
     * 修改接口
     */
    @PutMapping
    fun update(@RequestBody entity: Menu): ResponseEntity<*> {
        return ResponseEntity.ok(ResponseDto().apply {
            data = menuService.update(entity)
        })
    }

    /**
     * 删除接口（逻辑删除）
     */
    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") id: String?): ResponseEntity<*> {
        return ResponseEntity.ok(ResponseDto().apply {
            data = menuService.deleteById(id)
        })
    }
}