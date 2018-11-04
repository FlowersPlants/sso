package com.wang.sso.core.config

import com.wang.sso.core.support.BaseModel
import org.mybatis.spring.SqlSessionFactoryBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.annotation.Resource
import javax.sql.DataSource

/**
 * mapper.xml扫描器
 */
@Configuration
open class MyBatisConfig {

    @Resource
    private lateinit var dataSource: DataSource

    @Bean
    @Throws(Exception::class)
    open fun sqlSessionFactory(applicationContext: ApplicationContext): SqlSessionFactoryBean {
        val sessionFactory = SqlSessionFactoryBean()
        sessionFactory.setDataSource(dataSource)

        sessionFactory.setTypeAliasesPackage("com.wang.sso.modules")
        sessionFactory.setTypeAliasesSuperType(BaseModel::class.java)

        sessionFactory.setMapperLocations(applicationContext.getResources("classpath:mappings/*/*.xml"))

        //设置插件，比如分页插件
        //sessionFactory.setPlugins();

        //下划线转驼峰配置，可直接在application.yml中进行配置
        //sessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sessionFactory
    }
}