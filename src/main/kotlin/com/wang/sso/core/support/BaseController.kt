package com.wang.sso.core.support

import com.wang.sso.common.network.HttpUtils
import org.springframework.beans.factory.annotation.Autowired
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 控制器基类
 */
open class BaseController {

    @Autowired
    private lateinit var request: HttpServletRequest

    fun getRequest(): HttpServletRequest {
        return HttpUtils.getRequest()
    }

    fun getResponse(): HttpServletResponse? {
        return HttpUtils.getResponse()
    }
}