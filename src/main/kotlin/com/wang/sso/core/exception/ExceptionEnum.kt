package com.wang.sso.core.exception

/**
 * 异常枚举类
 */
enum class ExceptionEnum(val code: Int, val message: String) {
    /**
     * 普通业务异常
     */
    SERVICE_INSERT(701, "新增失败"),
    SERVICE_UPDATE(702, "更新失败"),
    SERVICE_DELETE(703, "删除失败"),

    /**
     * 系统业务
     */
    USERNAME_OR_PASSWORD_INCORRECT(601, "用户名或密码不正确"),
    ACCOUNT_LOCKED(602, "账号被锁定"),
    ACCOUNT_DISABLED(603, "账号被锁定");
}