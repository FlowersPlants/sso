package com.wang.sso.modules.sys.dao

import com.wang.sso.core.mybatis.annotation.MyBatisDao
import com.wang.sso.core.support.BaseDao
import com.wang.sso.modules.sys.entity.Menu

/**
 * 菜单管理数据库接口
 * @author FlowersPlants
 * @since v1
 */
@MyBatisDao(value = "menuDao", entity = Menu::class)
interface IMenuDao : BaseDao<Menu> {
    /**
     * 根据用户的所有角色ID获取所有菜单
     * @param list 角色ID集合
     */
    fun findByRoleIds(list: List<String?>): MutableList<Menu>?

    /**
     * 根据ID查询其本身和所有子节点
     */
    fun findChildrenById(id: String): MutableList<Menu>?
}
