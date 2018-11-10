package com.wang.sso.modules.sys.web

import com.wang.sso.common.dto.ResponseDto
import com.wang.sso.modules.sys.entity.User
import com.wang.sso.modules.sys.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 权限认证
 */
@RestController
class AuthController {

    @Autowired
    private lateinit var userService: UserService

    /**
     * 注册接口
     */
    @PostMapping
    @RequestMapping("/auth/register")
    fun register(@RequestBody user: User): ResponseEntity<*> {
        return ResponseEntity.ok(ResponseDto().apply {
            data = userService.insert(user)
        })
    }
}