package com.wang.sso.core.cache.redis.service.impl

import com.wang.sso.core.cache.redis.service.RedisService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

/**
 * redis data service impl
 * @author FlowersPlants
 * @since v1
 */
@Service
class RedisServiceImpl : RedisService {
    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, Any>

    override fun exists(key: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun set(key: String, value: String): String {
        return set(key, value, 0)
    }

    override fun set(key: String, value: String, seconds: Int?): String {
        if (seconds == null || seconds <= 0) {
            redisTemplate.opsForValue().set(key, value)
        } else {
            redisTemplate.opsForValue().set(key, value, seconds.toLong())
        }
        return key
    }

    override fun getSet(key: String, value: String, seconds: Int): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(key: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delKey(key: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delNativeKey(key: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMapData(key: String): Map<String, Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun lock(key: String, seconds: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unlock(key: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLockValue(key: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}