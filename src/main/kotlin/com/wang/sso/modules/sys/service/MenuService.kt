package com.wang.sso.modules.sys.service

import com.wang.sso.core.support.BaseService
import com.wang.sso.modules.sys.entity.Menu
import com.wang.sso.modules.sys.dto.MenuTree

/**
 * 菜单业务处理
 * @author FlowersPlants
 * @since v1
 */
interface MenuService : BaseService<Menu> {
    fun getUserMenuTree(): MutableList<MenuTree>
}