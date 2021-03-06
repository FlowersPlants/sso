package com.wang.sso.core.exception

/**
 * 自定义业务异常
 * @author FlowersPlants
 * @since v1
 */
class ServiceException() : SsoException() {
    private var code: Int? = null
    override var message: String? = null

    constructor(enum: ExceptionEnum) : this() {
        this.code = enum.code
        this.message = enum.message
    }

    constructor(code: Int, message: String) : this() {
        this.code = code
        this.message = message
    }
}