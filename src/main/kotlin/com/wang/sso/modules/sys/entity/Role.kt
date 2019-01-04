package com.wang.sso.modules.sys.entity

import com.baomidou.mybatisplus.annotation.TableName
import com.wang.sso.core.support.BaseModel

/**
 * 角色实体
 * @author FlowersPlants
 * @since v1
 */
@TableName("sys_role")
class Role() : BaseModel() {

    constructor(id: String) : this() {
        this.id = id
    }

    /**
     * 角色名称
     */
    var name: String? = null

    /**
     * 英文名称
     */
    var enname: String? = null

    /**
     * 角色类型（0-普通用户，1-超级管理员，2-系统管理员，3-普通管理员）
     */
    var type: String? = null

    /**
     * 排序号
     */
    var sort: Int? = null
}