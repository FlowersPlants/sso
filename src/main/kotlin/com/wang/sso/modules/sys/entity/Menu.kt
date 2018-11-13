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

    var url: String? = null

    var parent: Menu? = null

    var icon: String? = null

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