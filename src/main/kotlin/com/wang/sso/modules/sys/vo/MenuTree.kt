package com.wang.sso.modules.sys.vo

import com.wang.sso.common.dto.TreeNode

/**
 * 菜单树结构对象
 * @author FlowersPlants
 * @since v1
 */
class MenuTree : TreeNode() {
    companion object {
        const val serialVersionUID = 5640224091610000005L
    }

    /**
     * 菜单编码
     */
    var code: String? = null
    /**
     * 菜单类型（0:菜单组；1:菜单；）
     */
    var type: Int? = null
    /**
     * 菜单标题
     */
    var title: String? = null
    /**
     * 请求地址（前端）
     */
    var uri: String? = null
    /**
     * 图标名称
     */
    var icon: String? = null
}