package com.wang.sso.modules.sys.web

import com.wang.sso.common.utils.PaginationUtil
import com.wang.sso.core.support.BaseController
import com.wang.sso.modules.sys.entity.Dict
import com.wang.sso.modules.sys.service.DictService
import com.wang.sso.modules.sys.utils.DictUtils
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Api(tags = ["字典相关接口"])
@RestController
@RequestMapping("/sys/dict")
class DictController : BaseController() {
    @Autowired
    private lateinit var dictService: DictService

    /**
     * 获取所有字典并放在缓存中
     * 前端在获取用户信息后调用此方法来初始化全局公共数据
     */
    @ApiOperation("从redis缓存获取字典列表")
    @GetMapping("list")
    fun getDictList(): Any? {
        return DictUtils.getDictList(null)
    }

    @ApiOperation("字典管理分页接口，从缓存获取")
    @ApiImplicitParams(
        value = [
            ApiImplicitParam(name = "type", value = "字典类别", required = false),
            ApiImplicitParam(name = "current", value = "当前页", required = true),
            ApiImplicitParam(name = "pageSize", value = "每页数", required = true)
        ]
    )
    @GetMapping
    fun findPage(dict: Dict?): Any? {
        return dictService.findPage(dict, PaginationUtil.getRequestPage(getRequest())!!)
    }

    @ApiOperation("字典管理新增接口")
    @ApiImplicitParam(name = "dict", value = "字典对象", required = true)
    @PostMapping
    fun insert(@RequestBody dict: Dict?): Any? {
        return dictService.save(dict)
    }

    @ApiOperation("字典管理编辑接口")
    @ApiImplicitParam(name = "dict", value = "字典对象", required = true)
    @PutMapping
    fun update(@RequestBody dict: Dict?): Any? {
        return dictService.save(dict)
    }

    /**
     * 删除接口（逻辑删除）
     */
    @ApiOperation("字典管理逻辑删除接口")
    @ApiImplicitParam(name = "id", value = "字典对象主键ID", required = true)
    @DeleteMapping
    fun delete(id: String?): Any? {
        return dictService.deleteById(id)
    }
}
