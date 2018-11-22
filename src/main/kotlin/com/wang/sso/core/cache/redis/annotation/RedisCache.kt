package com.wang.sso.core.cache.redis.annotation

/**
 * redis cache注解
 * 代理service命中缓存则从缓存中读取数据，否则从service业务逻辑获得，并存入缓存
 * @param value 缓存的名称，需要保证不重复，逻辑操作里面做判断？
 * @param cacheTime 数据缓存时间，单位s，默认十分钟
 * @author FlowersPlants
 * @since v1
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class RedisCache(
    val value: String = "",
    val cacheTime: Int = 600
)