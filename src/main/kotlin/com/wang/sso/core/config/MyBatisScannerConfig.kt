package com.wang.sso.core.config

import com.wang.sso.core.annotations.MyBatisDao
import org.mybatis.spring.mapper.MapperScannerConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * dao 扫描器
 */
@Configuration
open class MyBatisScannerConfig {

    @Bean
    open fun mapperScannerConfigurer(): MapperScannerConfigurer {
        val mapperScannerConfigurer = MapperScannerConfigurer()
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory")

        mapperScannerConfigurer.setBasePackage("com.wang.sso.modules.**.dao")
        mapperScannerConfigurer.setAnnotationClass(MyBatisDao::class.java)//设置扫描基础包，被某个注解类标注的dao
        return mapperScannerConfigurer
    }
}