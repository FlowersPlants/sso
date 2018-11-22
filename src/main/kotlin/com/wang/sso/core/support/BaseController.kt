package com.wang.sso.core.support

import com.wang.sso.common.network.HttpUtils
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 控制器基类
 * @author FlowersPlants
 * @since v1
 */
abstract class BaseController {

    fun getRequest(): HttpServletRequest {
        return HttpUtils.getRequest()
    }

    fun getResponse(): HttpServletResponse? {
        return HttpUtils.getResponse()
    }
}