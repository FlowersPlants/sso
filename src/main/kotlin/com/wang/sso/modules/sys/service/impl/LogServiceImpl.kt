package com.wang.sso.modules.sys.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.wang.sso.core.exception.ExceptionEnum
import com.wang.sso.core.exception.ServiceException
import com.wang.sso.modules.sys.dao.ILogDao
import com.wang.sso.modules.sys.entity.Log
import com.wang.sso.modules.sys.service.LogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LogServiceImpl : LogService {
    @Autowired
    private lateinit var logDao: ILogDao

    override fun findPage(log: Log?, page: Page<Log>?): IPage<Log>? {
        return logDao.selectPage(page, QueryWrapper<Log>().apply {
            if (log != null) {
                if (!log.logType.isNullOrEmpty()) {
                    eq("log_type", "${log.logType}")
                }
                if (!log.logTitle.isNullOrEmpty()) {
                    like("log_title", "${log.logTitle}")
                }
                if (!log.hasException.isNullOrEmpty()) {
                    eq("has_exception", "${log.hasException}")
                }
                if (!log.requestMethod.isNullOrEmpty()) {
                    eq("request_method", "${log.requestMethod}")
                }
            }
            orderByDesc("create_time")
        })
    }

    override fun insert(log: Log?) {
        if (log != null) {
            val i = logDao.insert(log)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_INSERT)
            }
        }
    }
}
