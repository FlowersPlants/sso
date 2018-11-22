package com.wang.sso.core.cache.redis.aop

import com.wang.sso.common.utils.JsonUtils
import com.wang.sso.core.cache.redis.annotation.RedisCache
import com.wang.sso.core.cache.redis.service.RedisService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * redis cache 注解 aop解释器
 * @author FlowersPlants
 * @since v1
 */
@Aspect
@Component
class RedisCacheAspect {
    @Autowired
    private lateinit var redisService: RedisService

    @Pointcut("execution(public * com.wang.sso.modules.*.service..*.*(..))")
    fun webAspect() {
    }

    @Around("webAspect()")
    @Throws(Throwable::class)
    fun redisCache(joinPoint: ProceedingJoinPoint): Any? {
        val method = (joinPoint.signature as? MethodSignature)?.method
        var cacheAnnotation = method?.getAnnotation(RedisCache::class.java)

        cacheAnnotation = cacheAnnotation ?: return -1 // 不存在时直接退出
        var key = cacheAnnotation.value
        if (key == "") {
            val className = joinPoint.target.javaClass.name
            key = genKey(className, method?.name, joinPoint.args)
            System.err.println("生成的key是：$key")
        }

        val result: Any?
        val redisResult: String
        if (!redisService.exists(key)) {
            //缓存不存在，则调用原方法，并将结果放入缓存中
            result = joinPoint.proceed(joinPoint.args)
            redisResult = JsonUtils.toJson(result)
            redisService.set(key, redisResult, cacheAnnotation.cacheTime)
        } else {
            redisResult = redisService[key]
            //得到被代理方法的返回值类型
            val returnType = method!!.returnType
            result = JsonUtils.readValue(redisResult, returnType)
        }
        return result
    }

    /**
     * @Description: 生成key
     */
    private fun genKey(className: String, methodName: String?, args: Array<Any>): String {
        val sb = StringBuilder("CACHE_")
        sb.append(className.toUpperCase())
        sb.append("_")
        sb.append(methodName?.toUpperCase())
        sb.append("_")
        for ((index, obj) in args.withIndex()) {
            sb.append(obj.toString())
            if (index != args.size - 1)
                sb.append("_")
        }
        return sb.toString()
    }
}