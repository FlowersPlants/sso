package com.wang.sso.modules.sys.entity

import com.wang.sso.core.support.BaseModel
import java.util.*

/**
 * @author wzj
 */
open class User() : BaseModel<User>() {
    companion object {
        private const val serialVersionUID = 104343L
    }

    constructor(id: String) : this() {
        this.id = id
    }

    var account: String? = null

    var password: String? = null

    var enail: String? = null

    var gender: Boolean? = null

    var birthday: Date? = null
}