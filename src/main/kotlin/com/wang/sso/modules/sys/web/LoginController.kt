package com.wang.sso.modules.sys.web

import com.wang.sso.common.dto.ResponseDto
import com.wang.sso.core.support.BaseController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 登录控制器，不需要好像不行。。。怎么办？
 */
@RestController
@RequestMapping("/v1")
class LoginController : BaseController() {

    /**
     * 如果自动跳转到这里，说明用户为登录？
     * 如果要支持表单登录，可以在这个方法中判断请求的类型，进而决定返回JSON还是HTML页面？
     */
    @RequestMapping("login_age")
    fun login(): ResponseEntity<*> {
        return ResponseEntity.ok(ResponseDto().apply {
            val dto = ResponseDto()
            dto.code = 401
            dto.data = "未登录"
        })
    }
}