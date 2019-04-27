package com.wang.sso.core.logging.annotation

/**
 * 系统日志注解
 * @author FlowersPlants
 * @since v1
 * @param title 日志操作标题
 * @param type 日志类别，默认为查询类日志
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SystemLog(
    val title: String,
    val type: LogType = LogType.SELECT
)

/**
 * 日志操作类别
 * （unknown: 未知，access：接入日志；update：修改日志；select：查询日志；loginLogout：登录登出；）
 */
enum class LogType(var value: String) {
    UNKNOWN("unknown"),
    SELECT("select"),
    UPDATE("update"),
    ACCESS("access"),
    LOGIN_LOGOUT("loginLogout")
}
