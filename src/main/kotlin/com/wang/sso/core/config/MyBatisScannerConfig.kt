package com.wang.sso.core.config

import com.wang.sso.core.annotations.MyBatisDao
import org.mybatis.spring.mapper.MapperScannerConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * dao 扫描器
 * @author FlowersPlants
 * @since v1
 */
@Configuration
open class MyBatisScannerConfig {

    /**
     * 此处设置为静态方法的原因如下
     * Cannot enhance @Configuration bean definition 'myBatisScannerConfig' since its singleton instance has been created too early.
     * The typical cause is a non-static @Bean method with a BeanDefinitionRegistryPostProcessor return type: Consider declaring such methods as 'static'.
     */
    companion object {
        @Bean
        fun mapperScannerConfigurer(): MapperScannerConfigurer {
            val msc = MapperScannerConfigurer()
            msc.setSqlSessionFactoryBeanName("sqlSessionFactory")

            msc.setBasePackage("com.wang.sso.modules.*.dao")
            msc.setAnnotationClass(MyBatisDao::class.java)//设置扫描基础包，被某个注解类标注的dao
            return msc
        }
    }
}