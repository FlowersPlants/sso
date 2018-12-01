package com.wang.sso.modules.sys.entity

import com.baomidou.mybatisplus.annotation.TableName
import com.wang.sso.core.support.BaseModel
import java.util.*

/**
 * 用户实体
 * @author FlowersPlants
 * @since v1
 */
@TableName("sys_user")
open class User() : BaseModel() {
    companion object {
        private const val serialVersionUID = 104343L
    }

    constructor(id: String) : this() {
        this.id = id
    }

    /**
     * 用户账号，唯一
     */
    var account: String? = null

    var password: String? = null

    var email: String? = null

    var gender: Boolean? = null

    var birthday: Date? = null
}