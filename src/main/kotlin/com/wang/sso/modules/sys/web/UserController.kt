package com.wang.sso.modules.sys.web

import com.wang.sso.common.dto.ResponseDto
import com.wang.sso.core.support.BaseController
import com.wang.sso.modules.sys.entity.User
import com.wang.sso.modules.sys.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/sys/user")
class UserController : BaseController() {

    @Autowired
    private lateinit var userService: UserService

    @GetMapping
    fun list(user: User): ResponseEntity<*> {
        return ResponseEntity.ok(ResponseDto().apply {
            data = userService.findList(user)
        })
    }
}