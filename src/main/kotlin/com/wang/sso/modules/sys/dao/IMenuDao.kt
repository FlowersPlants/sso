package com.wang.sso.modules.sys.dao

import com.wang.sso.core.mybatis.annotation.MyBatisDao
import com.wang.sso.core.support.BaseDao
import com.wang.sso.modules.sys.entity.Menu
import org.apache.ibatis.annotations.Param

/**
 * 菜单管理数据库接口
 * @author FlowersPlants
 * @since v1
 */
@MyBatisDao(value = "menuDao", entity = Menu::class)
interface IMenuDao : BaseDao<Menu> {

    /**
     * 根据用户的所有角色ID获取所有菜单
     * @param roleIds 角色ID集合
     */
    fun findListByRoleIds(@Param("roleIds") roleIds: List<String?>?): MutableList<Menu>
}