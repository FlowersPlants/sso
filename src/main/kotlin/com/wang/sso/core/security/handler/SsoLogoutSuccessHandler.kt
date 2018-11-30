package com.wang.sso.core.security.handler

import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 登出成功处理，详情可查看{@see LogoutSuccessHandler}接口的多个默认实现
 * 如果用session保存用户信息 -> 设置会话失效
 * 如果是token -> 设置token过期
 * tip:此类必须要，因为默认的登出成功处理会进行url重定向
 * @author FlowersPlants
 * @date 2018-11-30
 */
@Service
class SsoLogoutSuccessHandler : HttpStatusReturningLogoutSuccessHandler() {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun onLogoutSuccess(p0: HttpServletRequest?, p1: HttpServletResponse?, p2: Authentication?) {
        logger.info("logout success.")
    }
}