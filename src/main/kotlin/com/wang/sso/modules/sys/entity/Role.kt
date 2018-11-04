package com.wang.sso.modules.sys.entity

import com.wang.sso.core.support.BaseModel

/**
 * @author wzj
 */
class Role() : BaseModel<Role>() {

    constructor(id: String) : this() {
        this.id = id
    }

    var enname: String? = null

    var type: String? = null
}