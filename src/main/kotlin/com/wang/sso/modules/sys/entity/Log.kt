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
//
//        // 日志类型（access：接入日志；update：修改日志；select：查询日志；loginLogout：登录登出；）
//        val TYPE_ACCESS = "access"
//        val TYPE_UPDATE = "update"
//        val TYPE_SELECT = "select"
//        val TYPE_LOGIN_LOGOUT = "loginLogout"
    }

    var logType: String? = null             // 日志类型
    var logTitle: String? = null            // 日志标题
//    var description: String? = null         // 日志描述
    var requestUri: String? = null          // 请求URI
    var requestUser: String? = null         // 操作用户的账号
    var requestMethod: String? = null       // 操作方式
    var requestParams: String? = null       // 操作提交的数据
    var responseResult: String? = null      // 响应结果
//    var bizKey: String? = null              // 业务主键
//    var bizType: String? = null             // 业务类型
    var remoteAddr: String? = null          // 操作IP地址
    var serverAddr: String? = null          // 请求服务器地址
    var hasException: String? = null        // 是否有异常
    var exceptionInfo: String? = null       // 异常信息
    var userAgent: String? = null           // 用户代理
    var deviceName: String? = null          // 设备名称/操作系统
    var browserName: String? = null         // 浏览器名称
    var executeTime: Long? = null           // 执行时间

    override fun toString(): String {
        return "Log(logType=$logType, logTitle=$logTitle, requestUri=$requestUri, requestUser=$requestUser, requestMethod=$requestMethod, requestParams=$requestParams, responseResult=$responseResult, remoteAddr=$remoteAddr, serverAddr=$serverAddr, hasException=$hasException, exceptionInfo=$exceptionInfo, userAgent=$userAgent, deviceName=$deviceName, browserName=$browserName, executeTime=$executeTime)"
    }
}
