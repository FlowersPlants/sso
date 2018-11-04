package com.wang.sso.core.exception

/**
 * 异常枚举类
 */
enum class ExceptionEnum(val code: Int, val message: String) {
    SERVICE_INSERT(701, "新增失败"),
    SERVICE_UPDATE(702, "更新失败"),
    SERVICE_DELETE(703, "删除失败");
}