package com.wang.sso.modules.sys.service

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.wang.sso.core.support.BaseService
import com.wang.sso.modules.sys.dto.RoleDto
import com.wang.sso.modules.sys.entity.Role
import java.io.Serializable

/**
 * 角色业务处理
 * @author FlowersPlants
 * @since v1
 */
interface RoleService : BaseService<Role> {
    fun findById(id: Serializable?): Role?

    fun findPage(entity: Role?, page: Page<Role>): IPage<Role>?

    /**
     * 批量新增（角色和菜单）或（角色和用户）中间记录（需要先执行删除操作）
     */
    fun insertBatchRecord(roleDto: RoleDto?)
}
