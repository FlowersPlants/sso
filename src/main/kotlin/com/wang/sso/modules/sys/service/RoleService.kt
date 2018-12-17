package com.wang.sso.modules.sys.service

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.wang.sso.core.support.BaseService
import com.wang.sso.modules.sys.entity.Role

/**
 * 角色业务处理
 * @author FlowersPlants
 * @since v1
 */
interface RoleService : BaseService<Role> {
    fun findPage(entity: Role?, page: Page<Role>): IPage<Role>?

    fun findList(entity: Role?): MutableList<Role>?
}