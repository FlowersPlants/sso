package com.wang.sso.modules.sys.entity

import com.baomidou.mybatisplus.annotation.TableName
import com.wang.sso.core.support.BaseModel

/**
 * 菜单实体
 * @author FlowersPlants
 * @since v1
 */
@TableName("sys_menu")
class Menu() : BaseModel() {

    constructor(id: String) : this() {
        this.id = id
    }

    /**
     * code
     */
    var code: String? = null

    /**
     * 菜单类型(0 - 菜单组； 1 - 菜单)
     */
    var type: String? = null

    /**
     * 后端请求链接
     */
    var url: String? = null

    /**
     * 前端url请求
     */
    var href: String? = null

    /**
     * 父ID
     */
    var parentId: String? = null

    /**
     * 图标名称
     */
    var icon: String? = null

    /**
     * 是否显示
     */
    var show: Boolean? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Menu

        if (this.id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}