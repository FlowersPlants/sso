package com.wang.sso.core.security.config

import com.wang.sso.core.filter.GlobalCorsWebFilter
import com.wang.sso.core.properties.SecurityProperties
import com.wang.sso.core.security.filter.SecurityAuthorizationFilter
import com.wang.sso.core.security.filter.SecurityLoginFilter
import com.wang.sso.core.security.handler.SsoFailureHandler
import com.wang.sso.core.security.handler.SsoLoginSuccessHandler
import com.wang.sso.core.security.handler.SsoLogoutSuccessHandler
import com.wang.sso.core.security.user.SecurityUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

/**
 * spring security 安全配置
 * @author FlowersPlants
 * @since v1
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(value = [SecurityProperties::class])
open class WebSecurityConfig() : WebSecurityConfigurerAdapter() {

    private lateinit var properties: SecurityProperties

    @Autowired
    constructor(properties: SecurityProperties) : this() {
        this.properties = properties
        System.err.println("security properties cors => ${properties.cors}")
    }

    /**
     * 添加userDetailsService的bean
     */
    @Bean
    public override fun userDetailsService(): UserDetailsService {
        return SecurityUserService()
    }

    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

//    /**
//     * cors跨域配置
//     * 跨域问题在没有设置bean.order = Ordered.HIGHEST_PRECEDENCE时都还存在
//     * 设置之后跨域问题解决
//     */
//    @Bean
//    open fun corsFilter(): FilterRegistrationBean<*> {
//        val source = UrlBasedCorsConfigurationSource()
//        val config = CorsConfiguration()
//        config.allowCredentials = true // 设置携带令牌
//        config.addAllowedHeader("*")
//        config.addAllowedMethod("*")
//        config.addAllowedOrigin("http://localhost:8080") // 设置允许跨域的域，此域为前端框架的域
//        source.registerCorsConfiguration("/**", config) // 对所有接口有效
//        val bean = FilterRegistrationBean<Filter>(CorsFilter(source))
//        bean.order = Ordered.HIGHEST_PRECEDENCE
//        return bean
//    }

    /**
     * 解决跨域的另一种方式，较上面的方式好
     * 好在allowedOrigin可动态获取
     */
    @Bean
    @ConditionalOnProperty(prefix = "security", name = ["cors"], havingValue = "true")
    open fun corsFilter(): GlobalCorsWebFilter {
        return GlobalCorsWebFilter()
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    /**
     * 自定义登录过滤器
     * 自定义之后登录失败和成功处理都不能使用Bean的方式
     * AntPathRequestMatcher设置登录处理URL(loginProcessingUrl)和有效的http method
     * 在这里搞来好久，直到设置（setRequiresAuthenticationRequestMatcher）之后此过滤器才生效
     */
    @Bean
    open fun securityLoginFilter(): SecurityLoginFilter {
        val filter = SecurityLoginFilter()
        filter.setAuthenticationManager(authenticationManagerBean())
        filter.setRequiresAuthenticationRequestMatcher(AntPathRequestMatcher("/auth/login", "POST"))
        filter.setAuthenticationSuccessHandler(SsoLoginSuccessHandler())
        filter.setAuthenticationFailureHandler(SsoFailureHandler())
        filter.usernameParameter = "account"
        filter.passwordParameter = "pwd"
        return filter
    }

    /**
     * 注入userDetailsService
     */
    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder())
    }

    /**
     * http访问配置，配置各个路径的访问权限
     * 登录和登出都是覆盖默认的
     */
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .antMatchers("/auth/register", "/swagger-ui.html/**", "/**/swagger-resources/**", "/v2/**", "/webjars/**")
            .permitAll() // 配置可匿名访问的URL

            .anyRequest()
            .authenticated()
            .and() // 其他任何请求，登录后可访问

            .formLogin()
            .permitAll()
            .and() // 配置表单登录相关

            .logout()
            .logoutUrl("/auth/logout")
            .logoutSuccessHandler(SsoLogoutSuccessHandler())
            .permitAll()
            .and() // 配置登出相关

            .csrf()
            .disable() // 关闭csrf

            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and() // 基于token，所以不需要session

            .addFilter(SecurityAuthorizationFilter(authenticationManagerBean()))
            .addFilterBefore(
                securityLoginFilter(),
                UsernamePasswordAuthenticationFilter::class.java
            ) // 自定义登录参数过滤器，加入到过滤器链
    }
}
