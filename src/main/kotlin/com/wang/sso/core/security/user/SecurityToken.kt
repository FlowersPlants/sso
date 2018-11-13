package com.wang.sso.core.security.user

import java.io.Serializable

/**
 * 登录令牌
 */
class SecurityToken() : Serializable {

    /**
     * token 令牌
     */
    var token: String? = null

    /**
     * 登陆时间戳（毫秒）
     */
    var loginTime: Long? = null

    constructor(token: String, loginTime: Long?) : this() {
        this.token = token
        this.loginTime = loginTime
    }

    companion object {
        private const val serialVersionUID = 6314027741784310221L
    }
}