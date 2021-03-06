package com.wang.sso.core.exception

/**
 * 自定义全局异常
 * @author FlowersPlants
 * @since v1
 */
open class SsoException() : RuntimeException() {

    private var code: Int? = null

    private var msg: String? = null

    constructor(message: String?):this(){
        this.code = 500
        this.msg = message
    }

    constructor(enum: ExceptionEnum) : this() {
        this.code = enum.code
        this.msg = enum.message
    }

    constructor(code: Int, message: String) : this() {
        this.code = code
        this.msg = message
    }

    /**
     * @param prefix message前缀，一起组合为具体的message
     * eg：username(prefix)不能为空(message)
     */
    constructor(code: Int, prefix: String, message: String) : this() {
        this.code = code
        this.msg = prefix + message
    }
}