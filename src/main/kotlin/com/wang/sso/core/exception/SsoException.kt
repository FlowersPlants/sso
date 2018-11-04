package com.wang.sso.core.exception

/**
 * 自定义全局异常
 */
class SsoException() : Exception() {

    var code: Int? = null

    override var message: String? = null

    constructor(enum: ExceptionEnum) : this() {
        this.code = enum.code
        this.message = enum.message
    }

    constructor(code: Int, message: String) : this() {
        this.code = code
        this.message = message
    }

    /**
     * @param prefix message前缀，一起组合为具体的message
     * eg：username(prefix)不能为空(message)
     */
    constructor(code: Int, prefix: String, message: String) : this() {
        this.code = code
        this.message = prefix + message
    }
}