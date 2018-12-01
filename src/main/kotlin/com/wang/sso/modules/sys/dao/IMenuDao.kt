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
    fun findListByRoleIds(@Param("roleIds") roleIds: List<String?>?): MutableList<Menu>
}