package com.wang.sso.core.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import java.security.Key
import java.time.Duration
import java.util.*
import javax.crypto.spec.SecretKeySpec

/**
 * 自定义jwt配置
 */
@ConfigurationProperties(prefix = "jwt")
class JwtProperties {
    private var expired = Duration.ofHours(24L)
    private var tokenHeader = "Authorization"
    private var randomKey = "7586df7fc3b34e26a66c039d5ec8445d"

    fun getKey(): Key {
        val encodedKey = Base64.getEncoder().encode(this.randomKey.toByteArray())
        return SecretKeySpec(encodedKey, 0, encodedKey.size, "HmacSHA256")
    }
}
