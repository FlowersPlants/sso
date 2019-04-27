package com.wang.sso.core.mybatis.config

import com.baomidou.mybatisplus.core.config.GlobalConfig
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean
import com.wang.sso.core.mybatis.handler.MyBatisPlusMetaObjectHandler
import com.wang.sso.core.support.BaseModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource


/**
 * MyBatis Plus插件配置，都从application.yml配置文件中独立出来
 * @author FlowersPlants
 * @since v1
 */
@Configuration
@EnableTransactionManagement
open class MyBatisConfig {

    @Autowired
    private lateinit var dataSource: DataSource
//
//    /**
//     * 逻辑删除时注入Bean
//     */
//    @Bean
//    open fun sqlInjector(): ISqlInjector {
//        return LogicSqlInjector()
//    }

    /**
     * mp字段自动填充Bean
     */
    @Bean
    open fun metaObjectHandler(): MetaObjectHandler {
        return MyBatisPlusMetaObjectHandler()
    }

    /**
     * 注入分页拦截器
     */
    @Bean
    open fun paginationInterceptor(): PaginationInterceptor {
        return PaginationInterceptor()
    }

    // 其他比如性能分析插件在开发时可配置

    /**
     * 用MybatisSqlSessionFactoryBean来替换SqlSessionFactoryBean
     * 解决MP Invalid bound statement (not found)异常
     */
    @Bean
    @Throws(Exception::class)
    open fun sqlSessionFactory(applicationContext: ApplicationContext): MybatisSqlSessionFactoryBean {
        val sessionFactory = MybatisSqlSessionFactoryBean()
        sessionFactory.setDataSource(dataSource)

        // 类型别名设置
        sessionFactory.setTypeAliasesPackage("com.wang.sso.modules.*.entity")
        sessionFactory.setTypeAliasesSuperType(BaseModel::class.java)

        // xml文件位置设置，配置在yml文件无效，什么原因呢？
        sessionFactory.setMapperLocations(applicationContext.getResources("classpath:mappings/*/*.xml"))

        // 全局配置
        sessionFactory.setGlobalConfig(globalConfig())

        // 设置插件，比如分页插件，必须设置分页插件分页功能才会生效
        // 设置数据库SQL语句格式化拦截器插件
        sessionFactory.setPlugins(arrayOf(paginationInterceptor()))

        // 下划线转驼峰配置，可直接在application.yml中进行配置
        // sessionFactory.getObject()!!.configuration.isMapUnderscoreToCamelCase = true
        return sessionFactory
    }

    /**
     * 自动填充插件，逻辑删除插件使用全局配置的方式注入
     */
    @Bean
    open fun globalConfig(): GlobalConfig {
        return GlobalConfig().setMetaObjectHandler(metaObjectHandler()) // 自动填充插件
//            .setSqlInjector(sqlInjector()) // 逻辑删除插件
    }
}
