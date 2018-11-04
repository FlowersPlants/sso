package com.wang.sso.common.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
open class ResponseDto : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }

    var data: Any? = null
    var code: Int? = 200

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var time: Date = Date()

    fun data(data: Any?): ResponseDto {
        this.data = data
        return this
    }
}