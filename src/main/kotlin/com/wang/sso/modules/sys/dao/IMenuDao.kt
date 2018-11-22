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

//    @Select("select a.* " +
//            "from sys_menu a " +
//            "left join sys_role_menu rm on rm.menu_id=a.id " +
//            "left join sys_role r on r.id=rm.role_id " +
//            "where r.id in #{roleIds}")
    fun findListByRoleIds(@Param("roleIds") roleIds: List<String?>?): MutableList<Menu>
}