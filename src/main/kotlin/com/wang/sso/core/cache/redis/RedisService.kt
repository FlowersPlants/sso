package com.wang.sso.core.cache.redis

import com.wang.sso.core.exception.SsoException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

/**
 * redis操作工具类，只列出常用操作；部分方法名称对应redis命令
 * @author FlowersPlants
 * @date 2018-12-02
 */
@Service
class RedisService {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, Any>

    /**
     * 指定缓存失效时间
     * redis: expire key timeout
     */
    fun expire(key: String, timeout: Long) {
        try {
            if (timeout > 0) {
                redisTemplate.expire(key, timeout, TimeUnit.SECONDS)
            }
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 获取缓存过期时间
     * redis: pttl key or ttl key
     */
    fun getExpire(key: String): Long {
        try {
            return redisTemplate.getExpire(key, TimeUnit.SECONDS)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 判断key是否存在
     * redis: exists key
     */
    fun exists(key: String): Boolean {
        return try {
            redisTemplate.hasKey(key)
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 删除缓存，可批量删除
     * kotlin可变参数需要解包
     */
    fun del(vararg keys: String) {
        try {
            redisTemplate.delete(mutableListOf(*keys))
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    // ===================== string ====================
    /**
     * 普通缓存保存
     * redis: set key value
     */
    fun set(key: String, value: Any) {
        try {
            val vo = redisTemplate.opsForValue()
            vo.set(key, value)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 普通缓存保存并设置过期时间
     * redis: set key value timeout
     * @param timeout 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    fun set(key: String, value: Any, timeout: Long) {
        try {
            val vo = redisTemplate.opsForValue()
            vo.set(key, value, timeout, TimeUnit.SECONDS)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 只有在 key 不存在时设置 key 的值
     * redis: setnx key value [ timeout ]
     */
    fun setnx(key: String, value: Any) {
        try {
            redisTemplate.opsForValue().setIfAbsent(key, value)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 同时设置一个或多个 key-value 对
     * redis: mset key value [ key value ]
     */
    fun mset( map: MutableMap<String, Any>) {
        try {
            redisTemplate.opsForValue().multiSet(map)
        }catch (e: Exception){
            throw SsoException(e.message)
        }
    }

    /**
     * 同时设置一个或多个 key-value 对，当且仅当key不存在时
     * redis: msetnx key value [ key value ]
     */
    fun msetnx(map: MutableMap<String, Any>) {
        try {
            redisTemplate.opsForValue().multiSetIfAbsent(map)
        }catch (e: Exception){
            throw SsoException(e.message)
        }
    }

    /**
     * 普通缓存获取
     * redis: get key
     */
    fun get(key: String): Any? {
        try {
            val vo = redisTemplate.opsForValue()
            return vo.get(key)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 获取一个或多个给定key的值
     * redis: mget key1 [ key2.. ]
     */
    fun mget(vararg keys: String): MutableList<*>? {
        return try {
            redisTemplate.opsForValue().multiGet(mutableListOf(*keys))
        } catch (e: Exception) {
            null
        }
    }

    // ======================= map ===================
    /**
     * 获取存储在hash表中指定字段的值
     * redis: hget key field
     */
    fun hget(key: String, field: String): Any? {
        try {
            val vo = redisTemplate.opsForHash<String, Any>()
            return vo.get(key, field)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 获取在哈希表中指定 key 的所有字段和值
     * redis: hgetall key
     */
    fun hgetall(key: String): MutableMap<String, Any> {
        try {
            return redisTemplate.opsForHash<String, Any>().entries(key)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中
     * redis: hmset key field1 value1 [ field2 value2 ]
     * @param map 多个键值
     */
    fun hmset(key: String, map: MutableMap<String, Any>) {
        try {
            return redisTemplate.opsForHash<String, Any>().putAll(key, map)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中，并设置过期时间
     * redis: hmset key field1 value1 [field2 value2 ]
     * @param map 多个键值
     */
    fun hmset(key: String, map: MutableMap<String, Any>, timeout: Long) {
        try {
            val vo = redisTemplate.opsForHash<String, Any>()
            vo.putAll(key, map)
            expire(key, timeout)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 将哈希表 key 中的字段 field 的值设为 value
     * redis: hset key field value
     */
    fun hset(key: String, field: String, value: Any) {
        try {
            redisTemplate.opsForHash<String, Any>().put(key, field, value)
            logger.info("保存缓存 {} 成功", key)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 只有在字段 field 不存在时，设置哈希表字段的值
     * redis: hsetnx key field value
     */
    fun hsetnx(key: String, field: String, value: Any): Boolean {
        return try {
            redisTemplate.opsForHash<String, Any>().putIfAbsent(key, field, value)
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 删除一个或多个哈希表字段
     * redis: hdel key field1 [ field2 ]
     */
    fun hdel(key: String, vararg fields: String) {
        try {
            redisTemplate.opsForHash<String, Any>().delete(key, fields)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    // ============================ list =======================
    /**
     * 获取列表指定范围内的元素
     * start stop 0到-1表示所有值
     * redis: lrange key start stop
     */
    fun lrange(key: String, start: Long, stop: Long): MutableList<Any>? {
        try {
            return redisTemplate.opsForList().range(key, start, stop)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 获取list缓存的长度
     * redis: llen key
     */
    fun llen(key: String): Long? {
        return try {
            redisTemplate.opsForList().size(key)
        } catch (e: Exception) {
            -1 // -1表示没有这个列表或者获取长度失败
        }
    }

    /**
     * 通过索引获取list缓存中的值
     * redis: lindex key index
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     */
    fun lindex(key: String, index: Long): Any? {
        try {
            return redisTemplate.opsForList().index(key, index)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 将一个或多个值插入到列表尾部
     * redis: rpush key value1 [ value2 ]
     */
    fun rpush(key: String, vararg values: String): Long? {
        return try {
            redisTemplate.opsForList().rightPushAll(key, values)
        } catch (e: Exception) {
            -1
        }
    }

    /**
     * 将一个或多个值插入到列表头部
     * redis: lpush key value1 [ value2 ]
     */
    fun lpush(key: String, vararg values: String): Long? {
        return try {
            redisTemplate.opsForList().leftPushAll(key, values)
        } catch (e: Exception) {
            -1
        }
    }

    /**
     * 将列表 key 下标为 index 的元素的值设置为 value（修改功能）
     * redis: lset key index value
     */
    fun lset(key: String, index: Long, value: Any) {
        try {
            redisTemplate.opsForList().set(key, index, value)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 移除列表元素
     * redis: lrem key count value
     */
    fun lrem(key: String, count: Long, value: Any): Long? {
        return try {
            redisTemplate.opsForList().remove(key, count, value)
        } catch (e: Exception) {
            0
        }
    }

    /**
     * 移除并获取列表的第一个元素，对应rpop命令
     * redis: lpop key
     */
    fun lpop(key: String): Any? {
        return try {
            redisTemplate.opsForList().leftPop(key)
        } catch (e: Exception) {
            null
        }
    }

    // ==================== set ========================
    /**
     * 向集合添加一个或多个成员
     * redis: sadd key member1 [ member2 ]
     */
    fun sadd(key: String, vararg values: Any) {
        try {
            redisTemplate.opsForSet().add(key, values)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 移除一个或多个元素
     * redis: srem key member1 [ member2 ]
     */
    fun srem(key: String, vararg values: Any): Long? {
        return try {
            redisTemplate.opsForSet().remove(key, values)
        } catch (e: Exception) {
            return -1
        }
    }
}