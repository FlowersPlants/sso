package com.wang.sso.modules.sys.dto

import com.wang.sso.common.dto.TreeNode

/**
 * 菜单树结构对象
 * @author FlowersPlants
 * @since v1
 */
class MenuTree : TreeNode() {
    companion object {
        const val serialVersionUID = 1L
    }

    /**
     * 菜单编码
     */
    var code: String? = null

    /**
     * 所有父ID
     */
    var parentIds: String? = null

    /**
     * 菜单类型(0 - 菜单组； 1 - 菜单)
     */
    var type: String? = null

    /**
     * 菜单名称
     */
    var name: String? = null

    /**
     * 请求地址（后端）
     */
    var url: String? = null

    /**
     * 请求地址（前端）
     */
    var href: String? = null

    /**
     * 图标名称
     */
    var icon: String? = null

    /**
     * 是否隐藏 0-显示，1-隐藏
     */
    var hidden: Boolean? = null
}
