package com.wang.sso.modules.sys.dto

import java.io.Serializable

/**
 * 角色dto对象
 * @author FlowersPlants
 * @date 2018-12-18
 */
class RoleDto : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }

    /**
     * 角色ID
     */
    var id: String? = null

    /**
     * 菜单ID集合
     */
    var menuIds: MutableList<String>? = null

    /**
     * 用户ID集合
     */
    var userIds: MutableList<String>? = null
}
