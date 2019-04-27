package com.wang.sso.core.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * ConfigurationProperties读自定义元数据
 * https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/html/configuration-metadata.html#configuration-metadata-annotation-processor
 */
@ConfigurationProperties(prefix = "security")
open class SecurityProperties {
    var cors = true
//    var checkAuthUrl = "/token/check"
//    var scan = false
}
