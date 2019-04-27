package com.wang.sso.core.logging.aop

import com.wang.sso.common.network.HttpUtils
import com.wang.sso.common.network.IpUtils
import com.wang.sso.common.utils.JsonUtils
import com.wang.sso.core.logging.annotation.SystemLog
import com.wang.sso.modules.sys.entity.Log
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.*
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component


/**
 * 日志aop方式保存
 * 会导致如下问题：当注解到controller上时，service注入失败；待解决
 * 注解到service是没问题的
 * https://www.cnblogs.com/wenjunwei/p/9639909.html
 * @author FlowersPlants
 * @since v1
 */
@Aspect
@Component
open class LogAspect {

    /**
     * 切入点，设置到自定义的log注解上
     * aop只拦截该注解对应的方法
     */
    @Pointcut("@annotation(com.wang.sso.core.logging.annotation.SystemLog)")
    fun pointCut() {
    }

    /**
     * 前置通知
     */
    @Before("com.wang.sso.core.logging.aop.LogAspect.pointCut()")
    fun saveLogBefore(point: JoinPoint) {
        println("before...")
    }

    /**
     * 环绕增强
     */
    @Around("com.wang.sso.core.logging.aop.LogAspect.pointCut()")
    fun around(point: ProceedingJoinPoint): Any? {
        println("around......")
        var result: Any? = null
        var time = System.currentTimeMillis()
        try {
            result = point.proceed()
            time = System.currentTimeMillis() - time
            return result
        } finally {
            addOperationLog(point, result, time)
        }
    }

    /**
     * 后置通知
     * @see pointCut
     */
    @After("com.wang.sso.core.logging.aop.LogAspect.pointCut()")
    fun saveLogAfter(point: JoinPoint) {
        println("after..")
    }

    /**
     * 处理完请求，返回内容
     * @param result
     */
    @AfterReturning(returning = "result", pointcut = "com.wang.sso.core.logging.aop.LogAspect.pointCut()")
    fun doAfterReturning(result: Any) {
        println("方法的返回结果 : $result")
    }

    /**
     * 后置异常通知
     */
    @AfterThrowing("com.wang.sso.core.logging.aop.LogAspect.pointCut()")
    fun saveLogThrow(jp: JoinPoint) {
        println("方法异常时执行.....")
    }

    /**
     * 保存操作日志
     */
    private fun addOperationLog(point: JoinPoint, result: Any?, time: Long) {
        val signature = point.signature as MethodSignature
        val request = HttpUtils.getRequest()
        val log = Log()
        log.executeTime = time
        log.requestParams = request.queryString
        log.responseResult = JsonUtils.toJson(result)
        log.remoteAddr = IpUtils.getRemoteAddr(request)
        log.requestUri = request.requestURI
        log.requestMethod = request.method
        val annotation = signature.method.getAnnotation(SystemLog::class.java)
        if (annotation != null) {
            log.logTitle = annotation.title
            log.logType = annotation.type.value
        }

        println("保存日志。。。$log")
    }
}
