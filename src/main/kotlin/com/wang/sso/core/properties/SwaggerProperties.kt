package com.wang.sso.core.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "swagger")
class SwaggerProperties {
    var open = false
}
