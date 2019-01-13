package com.wang.sso.modules.sys.service

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.wang.sso.core.support.BaseService
import com.wang.sso.modules.sys.entity.Dict

/**
 * 字典相关业务接口
 * @author FlowersPlants
 */
interface DictService : BaseService<Dict> {
    /**
     * 获取所有字典信息，需要放在缓存里面
     */
    fun findPage(dict: Dict?, page: Page<Dict>): IPage<Dict>?
}
