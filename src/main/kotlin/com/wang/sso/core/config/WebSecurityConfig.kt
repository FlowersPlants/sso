package com.wang.sso.core.config

import com.wang.sso.core.filter.SecurityAuthenticationFilter
import com.wang.sso.core.handler.SsoFailureHandler
import com.wang.sso.core.handler.SsoLoginSuccessHandler
import com.wang.sso.core.security.SecurityUserService
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import javax.servlet.Filter

/**
 * spring security 安全配置
 */
@Configuration
@EnableWebSecurity
open class WebSecurityConfig : WebSecurityConfigurerAdapter() {

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

// 如果不实用自定义登录方式，怎取消下面两个方法的注释，并且进行configure配置
//    @Bean
//    open fun loginSuccessHandler(): SavedRequestAwareAuthenticationSuccessHandler {
//        return SsoLoginSuccessHandler()
//    }
//
//    @Bean
//    open fun failureHandler(): SimpleUrlAuthenticationFailureHandler {
//        return SsoFailureHandler()
//    }

    /**
     * cors跨域配置
     * 跨域问题在没有设置bean.order = Ordered.HIGHEST_PRECEDENCE时都还存在
     * 设置之后跨域问题解决
     */
    @Bean
    open fun corsFilter() :FilterRegistrationBean<*>{
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        config.addAllowedOrigin("http://localhost:8080")
        source.registerCorsConfiguration("/**", config) // 对所有接口有效

        val bean = FilterRegistrationBean<Filter>(CorsFilter(source))
        bean.order = Ordered.HIGHEST_PRECEDENCE
        return bean
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    /**
     * 自定义登录认证过滤器
     * 自定义之后登录失败和成功处理都不能使用Bean的方式
     * AntPathRequestMatcher设置登录处理URL(loginProcessingUrl)和有效的http method
     * 在这里搞来好久，直到设置（setRequiresAuthenticationRequestMatcher）之后此过滤器才生效，待验证
     */
    @Bean
    open fun securityAuthenticationFilter(): SecurityAuthenticationFilter {
        val filter = SecurityAuthenticationFilter()
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

//    没有访问服务器的静态资源，所以取消此设置
//    /**
//     * 解决静态资源被拦截问题
//     * 当静态资源访问被拦截后自动跳转到login链接，导致浏览器请求报错误如下：
//     * because non script MIME types are not allowed when 'X-Content-Type: nosniff' is given.
//     * 因为login链接返回到不是标准到Content-Type,并且浏览器设置X-Content-Type: nosniff后不能猜测其Mime类型
//     * 参考链接https://blog.csdn.net/zhuyiquan/article/details/52173735
//     *
//     * 需要在[com.wang.sso.core.config.WebMvcConfig.addResourceHandlers]重新配置静态文件访问路径
//     */
//    override fun configure(web: WebSecurity?) {
//        web!!.ignoring().antMatchers("/static/**")
//    }

    /**
     * http访问配置，配置各个路径的访问权限
     */
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .antMatchers("/register")
            .permitAll() // 配置可匿名访问的URL

            .anyRequest()
            .authenticated()
            .and() // 其他任何请求，登录后可访问

            .formLogin()
            .loginPage("/login_page") // 这个可以不要
            .permitAll() // 设置表单登录和登录URL
//          .successHandler(loginSuccessHandler()) // 登录成功处理
//          .failureHandler(failureHandler()) // 登录失败处理
//          .usernameParameter("account")
//          .passwordParameter("pwd")
            .and() // 配置表单登录相关

            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/auth/login")
            .permitAll()
            .and() // 配置登出相关

            .csrf()
            .disable() // 关闭csrf

            .addFilterBefore(securityAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java) // 自定义登录参数过滤器
    }
}