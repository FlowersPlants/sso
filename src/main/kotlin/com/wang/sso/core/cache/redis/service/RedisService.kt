package com.wang.sso.core.cache.redis.service

/**
 * redis 数据相关操作接口
 * @author FlowersPlants
 * @since v1
 */
interface RedisService {
    /**
     * @Description: 是否存在
     * @Param:
     * @return:
     */
    fun exists(key: String): Boolean

    /**
     * @Description: 缓存set值，默认second为0
     * @param
     * @return
     */
    operator fun set(key: String, value: String): String

    /**
     * @Description:缓存set值
     * @Param:  seconds:缓存时间
     * @return:
     */
    fun set(key: String, value: String, seconds: Int?): String

    /**
     * @Description:  重新缓存getSet值
     * @Param:
     * @return:
     */
    fun getSet(key: String, value: String, seconds: Int): String

    /**
     * @Description: 获取set值
     * @Param:
     * @return:
     */
    operator fun get(key: String): String

//    /**
//     * @Description: 添加地理位置
//     * @Param:
//     * @return:
//     */
//    fun geoadd(key: String, longitude: Double, latitude: Double, obj: ByteArray): Long?

//    /**
//     * @Description: 地理位置查询
//     * @Param:
//     * @return:
//     */
//    fun georadius(key: String, longitude: Double, latitude: Double): List<Geo>

    /**
     * @Description: 删除key
     * @Param:
     * @return:
     */
    fun delKey(key: String)

    /**
     * @Description: 删除native key
     * @Param:
     * @return:
     */
    fun delNativeKey(key: String)

    /**
     * @Description: 获取map格式的数据
     * @Param:
     * @return:
     */
    fun getMapData(key: String): Map<String, Any>

    /**
     * @Description: 加锁，避免重复提交
     * @Param:
     * @return:
     */
    fun lock(key: String, seconds: Int): Boolean

    /**
     * @Description: 解锁
     * @Param:
     * @return:
     */
    fun unlock(key: String)

    /**
     * @Description: 统计锁定次数
     * @Param:
     * @return:
     */
    fun getLockValue(key: String): String
}