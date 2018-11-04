package com.wang.sso.core.annotations

/**
 * 系统日志注解,逻辑待实现
 *
 * @author wzj
 * @date 2018-11-04
 * @since v2
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Log(val value: String = "")