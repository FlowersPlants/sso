package com.wang.sso.modules.sys.service

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.wang.sso.modules.sys.entity.Log

/**
 * 日志记录相关业务接口
 * @author FlowersPlants
 */
interface LogService {
    /**
     * 分页查询接口
     * @param log 可传参数如下：{logType, logTitle, bizType, hasException, requestMethod}等
     */
    fun findPage(log: Log?, page: Page<Log>?): IPage<Log>?

    /**
     * 日志新增接口，不使用数据库事务
     */
    fun insert(log: Log?)
}