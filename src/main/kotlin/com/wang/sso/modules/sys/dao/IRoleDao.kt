package com.wang.sso.modules.sys.dao

import com.wang.sso.core.mybatis.annotation.MyBatisDao
import com.wang.sso.core.support.BaseDao
import com.wang.sso.modules.sys.dto.RoleDto
import com.wang.sso.modules.sys.entity.Role

/**
 * 角色管理数据库操作接口
 * @author FlowersPlants
 * @since v1
 */
@MyBatisDao(value = "roleDao", entity = Role::class)
interface IRoleDao : BaseDao<Role> {
    /**
     * 根据用户ID获取其所有角色
     * @param userId 用户ID
     */
    fun findByUserId(userId: String): MutableList<Role>?

    /**
     * 批量新增之前先删除记录
     */
    fun deleteRoleMenuByRoleId(roleDto: RoleDto): Int

    fun deleteRoleUserByRoleId(roleDto: RoleDto): Int

    /**
     * 批量新增角色和菜单中间表记录
     */
    fun insertBatchMenuRecord(roleDto: RoleDto): Int

    /**
     * 批量新增角色和用户中间记录
     */
    fun insertBatchUserRecord(roleDto: RoleDto): Int
}
