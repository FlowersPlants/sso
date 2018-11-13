package com.wang.sso.core.config

import com.baomidou.mybatisplus.core.config.GlobalConfig
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import com.baomidou.mybatisplus.core.injector.ISqlInjector
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean
import com.wang.sso.core.mybatis.MyBatisPlusMetaObjectHandler
import com.wang.sso.core.support.BaseModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource


/**
 * mapper.xml扫描器
 * @author FlowersPlants
 * @since v1
 */
@Configuration
@EnableTransactionManagement
open class MyBatisConfig {

    @Autowired
    private lateinit var dataSource: DataSource

    /**
     * 逻辑删除时注入Bean
     */
    @Bean
    open fun sqlInjector(): ISqlInjector {
        return LogicSqlInjector()
    }

    /**
     * 注入分页拦截器
     */
    @Bean
    open fun paginationInterceptor(): PaginationInterceptor {
        return PaginationInterceptor()
    }

    /**
     * mp字段自动填充Bean
     */
    @Bean
    open fun metaObjectHandler(): MetaObjectHandler {
        return MyBatisPlusMetaObjectHandler()
    }

    /**
     * 用MybatisSqlSessionFactoryBean来替换SqlSessionFactoryBean
     * 解决MP Invalid bound statement (not found)异常
     */
    @Bean
    @Throws(Exception::class)
    open fun sqlSessionFactory(applicationContext: ApplicationContext): MybatisSqlSessionFactoryBean {
        val sessionFactory = MybatisSqlSessionFactoryBean()
        sessionFactory.setDataSource(dataSource)

        sessionFactory.setTypeAliasesPackage("com.wang.sso.modules.*.entity")
        sessionFactory.setTypeAliasesSuperType(BaseModel::class.java)

        sessionFactory.setMapperLocations(applicationContext.getResources("classpath:mappings/*/*.xml"))

        sessionFactory.setGlobalConfig(globalConfig())

        //设置插件，比如分页插件，必须设置分页插件才会生效
        sessionFactory.setPlugins(arrayOf(paginationInterceptor()))

        //下划线转驼峰配置，可直接在application.yml中进行配置
        //sessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sessionFactory
    }

    /**
     * 全局自动注入插件，逻辑删除插件
     */
    @Bean
    open fun globalConfig(): GlobalConfig {
        return GlobalConfig()
            .setMetaObjectHandler(metaObjectHandler())
            .setSqlInjector(sqlInjector())
    }
}