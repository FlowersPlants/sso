package com.wang.sso.core.swagger

import com.wang.sso.core.properties.SwaggerProperties
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 * swagger2 接口文档配置
 */
@Configuration
@EnableSwagger2
@EnableConfigurationProperties(value = [SwaggerProperties::class])
@ConditionalOnProperty(prefix = "swagger", name = ["open"], havingValue = "true")
open class SwaggerConfig() {
    private lateinit var properties: SwaggerProperties

    @Autowired
    constructor(properties: SwaggerProperties) : this() {
        this.properties = properties
        System.err.println("swagger properties open => ${properties.open}")
    }

    @Bean
    open fun createApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .groupName("v1")
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation::class.java))
            .paths(PathSelectors.any())
            .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("Sso 单点登录")
            .description("Sso Api Document")
            .termsOfServiceUrl("")
            .contact(Contact("FlowersPlants", "https://github.com/FlowersPlants", ""))
            .version("1.0")
//            .license("!!!private!!!")
            .build()
    }
}
