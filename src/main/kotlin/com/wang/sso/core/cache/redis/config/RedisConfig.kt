package com.wang.sso.core.cache.redis.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisClusterConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import java.time.Duration

/**
 * redis 配置，目前无缓存功能，此配置无效
 * @author FlowersPlants
 * @since v1
 */
@Configuration
@EnableCaching
open class RedisConfig {
    @Autowired
    private lateinit var properties: RedisProperties

    @Bean
    open fun lettuceConnectionFactory(): LettuceConnectionFactory {
        val redisClusterConfiguration = RedisClusterConfiguration(properties.cluster.nodes)
        val poolConfig = GenericObjectPoolConfig().apply {
            maxIdle = 10 // 最大空闲连接数
            maxTotal = 20 // 最大连接数
            minIdle = 0 // 最小空闲连接数
        }
        val lettuceClientConfiguration = LettucePoolingClientConfiguration
            .builder()
            .commandTimeout(Duration.ofSeconds(15))
            .poolConfig(poolConfig)
            .shutdownTimeout(Duration.ZERO)
            .build()
        return LettuceConnectionFactory(redisClusterConfiguration, lettuceClientConfiguration)
    }

    @Bean
    open fun jackson2JsonRedisSerializer(): Jackson2JsonRedisSerializer<*> {
        val om = ObjectMapper().apply {
            this.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
            this.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
        }
        return Jackson2JsonRedisSerializer(Any::class.java).apply {
            this.setObjectMapper(om)
        }
    }

    @Bean
    open fun redisTemplate(): RedisTemplate<String, Any> {
        return RedisTemplate<String, Any>().apply {
            this.valueSerializer = jackson2JsonRedisSerializer()
            this.connectionFactory = lettuceConnectionFactory()
            this.keySerializer = JdkSerializationRedisSerializer()
            this.afterPropertiesSet()
        }
    }
}