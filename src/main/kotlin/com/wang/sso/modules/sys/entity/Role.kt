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

    var enname: String? = null

    var type: String? = null
}