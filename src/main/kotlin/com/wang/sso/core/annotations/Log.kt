package com.wang.sso.core.annotations

/**
 * 系统日志注解,逻辑待实现
 * @author FlowersPlants
 * @since v1
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Log(val value: String = "")