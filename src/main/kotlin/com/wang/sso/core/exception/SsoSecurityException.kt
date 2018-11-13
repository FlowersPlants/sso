package com.wang.sso.core.exception

import org.springframework.security.core.AuthenticationException

/**
 * 自定义认证相关异常
 * @author FlowersPlants
 * @since v1
 */
class SsoSecurityException(message: String) : AuthenticationException(message) {
    private var code: Int? = null
    override var message: String? = null

    init {
        this.message = message
    }

    constructor(code: Int, message: String) : this(message) {
        this.message = message
        this.code = code
    }

    constructor(enum: ExceptionEnum) : this(enum.message) {
        this.code = enum.code
        this.message = enum.message
    }
}