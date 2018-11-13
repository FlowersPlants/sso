package com.wang.sso.core.annotations

import org.springframework.stereotype.Component
import kotlin.reflect.KClass

/**
 * mybatis注解，标注需要Springboot扫描的dao接口
 * @author FlowersPlants
 * @since v1
 */
@Component
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class MyBatisDao(val value: String = "", val entity: KClass<*> = Class::class)