package com.wang.sso.common.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import java.util.*

/**
 * 统一返回json数据格式的dto对象
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
open class ResponseDto : Serializable {
    companion object {
        private const val serialVersionUID = 1L
        const val OK = 200
        const val ERROR = 500
    }

    var data: Any? = null
    var code: Int? = OK

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var time: Date = Date()
}