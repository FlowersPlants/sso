package com.wang.sso.modules.sys.web

import com.wang.sso.common.dto.ResponseDto
import com.wang.sso.common.utils.PaginationUtil
import com.wang.sso.core.support.BaseController
import com.wang.sso.modules.sys.entity.Dict
import com.wang.sso.modules.sys.service.DictService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sys/dict")
class DictController : BaseController() {
    @Autowired
    private lateinit var dictService: DictService

    @GetMapping
    fun findPage(dict: Dict?): Any? {
        // return dictService.findPage(dict, PaginationUtil.getRequestPage(getRequest()))
        return ResponseDto().apply {
            data = dictService.findPage(dict, PaginationUtil.getRequestPage(getRequest()))
        }
    }

    @PostMapping
    fun insert(@RequestBody dict: Dict?): Any? {
        return ResponseDto().apply {
            data = dictService.save(dict)
        }
    }

    @PutMapping
    fun update(@RequestBody dict: Dict?): Any? {
        return ResponseDto().apply {
            data = dictService.save(dict)
        }
    }

    /**
     * 删除接口（逻辑删除）
     */
    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") id: String?): Any? {
        return ResponseDto().apply {
            data = dictService.deleteById(id)
        }
    }
}
