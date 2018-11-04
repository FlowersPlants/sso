package com.wang.sso.modules.sys.vo

import com.wang.sso.modules.sys.entity.Role
import java.util.*

/**
 * user vo
 * 值对象
 */
class UserVo {
    var id: String? = null

    var username: String? = null

    var passwod: String? = null

    var enail: String? = null

    var gender: Boolean? = null

    var birthday: Date? = null

    var status: String? = null

    var roles: MutableList<Role>? = null
}