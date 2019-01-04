package com.wang.sso.modules.sys.entity

import com.baomidou.mybatisplus.annotation.TableName
import com.wang.sso.core.support.BaseModel

/**
 * 系统日志实体
 */
@TableName("sys_log")
class Log : BaseModel() {
    companion object {
        private const val serialVersionUID = 1L

        // 日志类型（access：接入日志；update：修改日志；select：查询日志；loginLogout：登录登出；）
        val TYPE_ACCESS = "access"
        val TYPE_UPDATE = "update"
        val TYPE_SELECT = "select"
        val TYPE_LOGIN_LOGOUT = "loginLogout"
    }

    val logType: String? = null             // 日志类型
    val logTitle: String? = null            // 日志标题
    val requestUri: String? = null          // 请求URI
    val requestMethod: String? = null       // 操作方式
    val requestParams: String? = null       // 操作提交的数据
    val bizKey: String? = null              // 业务主键
    val bizType: String? = null             // 业务类型
    val remoteAddr: String? = null          // 操作IP地址
    val serverAddr: String? = null          // 请求服务器地址
    val hasException: String? = null        // 是否有异常
    val exceptionInfo: String? = null       // 异常信息
    val userAgent: String? = null           // 用户代理
    val deviceName: String? = null          // 设备名称/操作系统
    val browserName: String? = null         // 浏览器名称
    val executeTime: Long? = null           // 执行时间
}
