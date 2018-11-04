/**
 * 这个配置目前没发现有什么用。。
 */
//package com.wang.sso.core.config
//
//import com.wang.sso.core.interceptor.LoginInterceptor
//import org.springframework.context.annotation.Configuration
//import org.springframework.web.servlet.config.annotation.EnableWebMvc
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
//
//
///**
// * web mvc配置
// */
//@EnableWebMvc
//@Configuration
//open class WebMvcConfig : WebMvcConfigurationSupport() {
//    override fun addInterceptors(registry: InterceptorRegistry) {
//        registry.addInterceptor(LoginInterceptor())
//    }
//
////    /**
////     * 跨越问题
////     */
////    override fun addCorsMappings(registry: CorsRegistry) {
////        registry.addMapping("/**")
////            .allowedOrigins("*")
////            .allowedMethods("*")
////            .allowedHeaders("*")
////            .allowCredentials(true)
////    }
//
//    // 取消
////    override fun addResourceHandlers(registry: ResourceHandlerRegistry?) {
////        registry!!.addResourceHandler("/static/**").addResourceLocations("classpath:/static/")
////    }
//}