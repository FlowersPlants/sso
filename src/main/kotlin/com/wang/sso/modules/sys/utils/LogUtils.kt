package com.wang.sso.modules.sys.utils

import com.wang.sso.common.utils.SpringUtils
import com.wang.sso.modules.sys.entity.Log
import com.wang.sso.modules.sys.service.LogService
import org.springframework.core.DefaultParameterNameDiscoverer
import org.springframework.core.ParameterNameDiscoverer
import javax.servlet.http.HttpServletRequest

/**
 * 日志工具类
 */
object LogUtils {
    class Static {
        val logService: LogService = SpringUtils.getBean(LogService::class.java)
    }

    val pnd: ParameterNameDiscoverer = DefaultParameterNameDiscoverer()

    fun saveLog(request: HttpServletRequest?, handler: Any?, exception: Exception?) {
        val log = Log()
        SaveLogThread(log, handler!!, request!!.contextPath, exception!!)
    }

    class SaveLogThread() : Thread() {
        private var log: Log? = null
        private var handler: Any? = null
        private var contextPath: String? = null
        private var throwable: Throwable? = null

        constructor(log: Log, handler: Any, contextPath: String, throwable: Throwable) : this() {
            this.log = log
            this.handler = handler
            this.contextPath = contextPath
            this.throwable = throwable
        }

        override fun run() {
            println("异步保存日志")
            super.run()
            Static().logService.insert(log)
        }
    }
}
