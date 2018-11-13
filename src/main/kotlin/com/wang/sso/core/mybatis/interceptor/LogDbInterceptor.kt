package com.wang.sso.core.mybatis.interceptor

import org.apache.ibatis.executor.statement.StatementHandler
import org.apache.ibatis.plugin.Interceptor
import org.apache.ibatis.plugin.Intercepts
import org.apache.ibatis.plugin.Invocation
import org.apache.ibatis.plugin.Signature
import org.springframework.stereotype.Component
import java.sql.Connection
import java.util.*

/**
 * MyBatis SQL操作语句格式化拦截器
 * 目前未做实现
 * @author FlowersPlants
 * @since v2
 */
@Intercepts(Signature(type = StatementHandler::class, method = "prepare", args = [Connection::class, Integer::class]))
@Component
class LogDbInterceptor : Interceptor {
    override fun intercept(invocation: Invocation): Any {
        println("LogDbInterceptor.intercept(Invocation)......")
        return invocation.proceed()
    }

    override fun plugin(target: Any): Any {
        println("LogDbInterceptor.plugin(Any)......")
        return target
    }

    override fun setProperties(properties: Properties?) {
        println("LogDbInterceptor.setProperties(Properties)......")
    }
}