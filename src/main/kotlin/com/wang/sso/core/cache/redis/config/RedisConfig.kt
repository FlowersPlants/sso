package com.wang.sso.core.cache.redis.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.transaction.annotation.EnableTransactionManagement


/**
 * redis 配置
 * @author FlowersPlants
 * @since v1
 */
@Configuration
@EnableTransactionManagement // 事务控制
open class RedisConfig {

    @Autowired
    private lateinit var lettuceConnectionFactory: LettuceConnectionFactory

    @Bean
    open fun redisTemplate(): RedisTemplate<String, Any> {
        return RedisTemplate<String, Any>().apply {
            val redisSerializer = StringRedisSerializer()
            this.valueSerializer = jackson2JsonRedisSerializer()
            this.connectionFactory = lettuceConnectionFactory
            this.keySerializer = redisSerializer
            this.hashKeySerializer = redisSerializer
            this.valueSerializer = jackson2JsonRedisSerializer()
            this.hashValueSerializer = jackson2JsonRedisSerializer()
            this.afterPropertiesSet()
        }
    }

    /**
     * 序列化配置，RedisTemplate默认是JdkSerializationRedisSerializer；
     * ObjectMapper设置需完善
     * 1、JdkSerializationRedisSerializer：被序列化对象必须实现Serializable接口，被序列化除属性内容还有其他内容，长度长且不易阅读
     * 2、StringRedisSerializer：一般如果key、value是字符串的话，就用这个
     * 3、Jackson2JsonRedisSerializer：被序列化对象不需要实现Serializable接口，被序列化的结果清晰，容易阅读，而且存储字节少，速度快
     * 4、GenericToStringSerializer：可以将任何对象泛化为字符串并序列化
     */
    @Bean
    open fun jackson2JsonRedisSerializer(): Jackson2JsonRedisSerializer<*> {
        val om = ObjectMapper().apply {
            this.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
            this.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
            this.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        }
        return Jackson2JsonRedisSerializer(Any::class.java).apply {
            this.setObjectMapper(om)
        }
    }
}