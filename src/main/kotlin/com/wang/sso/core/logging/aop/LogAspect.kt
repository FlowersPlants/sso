//package com.wang.sso.core.logging.aop
//
//import org.aspectj.lang.JoinPoint
//import org.aspectj.lang.annotation.Aspect
//import org.aspectj.lang.annotation.Before
//import org.aspectj.lang.annotation.Pointcut
//import org.springframework.stereotype.Component
//
///**
// * 日志aop方式保存，待实现
// * https://blog.csdn.net/u011521890/article/details/74990338
// * @author FlowersPlants
// * @since v1
// */
//@Aspect
//@Component
//class LogAspect {
//
//    /**
//     * 切入点，设置到自定义的log注解上
//     * aop只拦截该注解对应的方法
//     */
//    @Pointcut("@annotation(com.wang.sso.core.logging.annotation.Log)")
//    private fun pointCut(){}
//
//    @Before("com.wang.sso.core.logging.aop.LogAspect.pointCat()")
//    fun saveLog(joinPoint: JoinPoint){}
//}