package com.wang.sso.core.cache.redis

import com.wang.sso.core.exception.SsoException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

/**
 * redis操作工具类
 * 还不完整，来源：https://www.cnblogs.com/zeng1994/p/03303c805731afc9aa9c60dbbd32a323.html
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
     */
    fun getExprie(key: String): Long {
        try {
            return redisTemplate.getExpire(key, TimeUnit.SECONDS)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 判断key是否存在
     */
    fun hasKey(key: String): Boolean {
        try {
            return redisTemplate.hasKey(key)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 删除缓存
     */
    fun del(key: String) {
        try {
            redisTemplate.delete(key)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    // ===================== string ====================
    /**
     * 普通缓存保存
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
     * 普通缓存获取
     */
    fun get(key: String): Any? {
        try {
            val vo = redisTemplate.opsForValue()
            return vo.get(key)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    // ======================= map ===================
    /**
     * hash 缓存获取
     */
    fun getHashValue(key: String, item: String): Any? {
        try {
            val vo = redisTemplate.opsForHash<String, Any>()
            return vo.get(key, item)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 获取hash key对应的所有键值
     */
    fun getHashMap(key: String): MutableMap<String, Any> {
        try {
            return redisTemplate.opsForHash<String, Any>().entries(key)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * hash 缓存设置
     * @param map 多个键值
     */
    fun setHashMap(key: String, map: MutableMap<String, Any>) {
        try {
            return redisTemplate.opsForHash<String, Any>().putAll(key, map)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * hash 缓存设置并设置过期时间
     * @param map 多个键值
     */
    fun setHashMap(key: String, map: MutableMap<String, Any>, timeout: Long) {
        try {
            val vo = redisTemplate.opsForHash<String, Any>()
            vo.putAll(key, map)
            expire(key, timeout)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 向hash设置缓存
     */
    fun setHashMap(key: String, item: String, value: Any) {
        try {
            redisTemplate.opsForHash<String, Any>().put(key, item, value)
            logger.info("保存缓存 {} 成功", key)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 删除hash缓存里面的多个字段
     */
    fun delHashMap(key: String, vararg item: String) {
        try {
            redisTemplate.opsForHash<String, Any>().delete(key, item)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    // ============================ list =======================
    /**
     * 获取list缓存内容
     * start end 0到-1表示所有值
     */
    fun getList(key: String, start: Long, end: Long): MutableList<Any>? {
        try {
            return redisTemplate.opsForList().range(key, start, end)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 获取list缓存的长度
     */
    fun getListSize(key: String): Long? {
        try {
            return redisTemplate.opsForList().size(key)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 通过索引获取list缓存中的值
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     */
    fun getValueByIndex(key: String, index: Long): Any? {
        try {
            return redisTemplate.opsForList().index(key, index)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 将list放入缓存
     */
    fun setList(key: String, value: Any) {
        try {
            redisTemplate.opsForList().rightPush(key, value)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 将list放入缓存，并设置过期时间
     */
    fun setList(key: String, value: Any, timeout: Long) {
        try {
            redisTemplate.opsForList().rightPush(key, value)
            expire(key, timeout)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 根据索引修改list缓存中的某条数据
     */
    fun updateListByIndex(key: String, index: Long, value: Any) {
        try {
            redisTemplate.opsForList().set(key, index, value)
        } catch (e: Exception) {
            throw SsoException(e.message)
        }
    }

    /**
     * 移除count个值为value的缓存
     */
    fun removeListCount(key: String, count: Long, value: Any): Long? {
        return try {
            redisTemplate.opsForList().remove(key, count, value)
        } catch (e: Exception) {
            0
        }
    }

    // ==================== set ========================
}