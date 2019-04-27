package com.wang.sso.core.mybatis.generator

import com.baomidou.mybatisplus.annotation.DbType
import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.generator.AutoGenerator
import com.baomidou.mybatisplus.generator.InjectionConfig
import com.baomidou.mybatisplus.generator.config.*
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert
import com.baomidou.mybatisplus.generator.config.po.TableFill
import com.baomidou.mybatisplus.generator.config.po.TableInfo
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType
import com.baomidou.mybatisplus.generator.config.rules.IColumnType
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy
import java.util.*

/**
 * MySQL generator
 * 需要vm文件，mybatis-plus 3.1.1版本移除了generator的依赖，可按需引入
 * @author FlowersPlants
 * @since v1
 */
object MysqlGenerator {
    @JvmStatic
    fun main(args: Array<String>) {
        generator()
    }

    fun generator() {
        // 自定义需要填充的字段
        val tableFillList = ArrayList<TableFill>()
        tableFillList.add(TableFill("createAt", FieldFill.INSERT))
        tableFillList.add(TableFill("updateAt", FieldFill.UPDATE))

        // 代码生成器
        val mpg = AutoGenerator().setGlobalConfig(
            // 全局配置
            GlobalConfig()
                .setOutputDir("/develop/code/")//输出目录
                .setFileOverride(true)// 是否覆盖文件
                .setActiveRecord(false)// 开启 activeRecord 模式
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(false)// XML ResultMap
                .setBaseColumnList(false)// XML columList
                .setKotlin(true) //是否生成 kotlin 代码
                .setAuthor("FlowersPlants") //作者
                //自定义文件命名，注意 %s 会自动填充表实体属性！
                .setEntityName("%s")
                .setMapperName("I%sDao")
                .setXmlName("%sDao")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController")
        ).setDataSource(
            // 数据源配置
            DataSourceConfig()
                .setDbType(DbType.MYSQL)// 数据库类型
                .setTypeConvert(object : MySqlTypeConvert() {
                    override fun processTypeConvert(globalConfig: GlobalConfig, fieldType: String): IColumnType {
                        if (fieldType.toLowerCase().contains("bit")) {
                            return DbColumnType.BOOLEAN
                        }
                        if (fieldType.toLowerCase().contains("tinyint")) {
                            return DbColumnType.BOOLEAN
                        }
                        if (fieldType.toLowerCase().contains("date")) {
                            return DbColumnType.LOCAL_DATE
                        }
                        if (fieldType.toLowerCase().contains("time")) {
                            return DbColumnType.LOCAL_TIME
                        }
                        return if (fieldType.toLowerCase().contains("datetime")) {
                            DbColumnType.LOCAL_DATE_TIME
                        } else super.processTypeConvert(globalConfig, fieldType)
                    }
                })
                .setDriverName("com.mysql.jdbc.Driver")
                .setUsername("root")
                .setPassword("root")
                .setUrl("jdbc:mysql://127.0.0.1:3306/ssms_sso_auth?characterEncoding=utf8")
        ).setStrategy(
            // 策略配置
            StrategyConfig()
                .setCapitalMode(false)// 全局大写命名
                .setTablePrefix("sys_")// 去除前缀
                .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                //.setInclude(String[] { "user" }) // 需要生成的表
                //自定义实体父类
                .setSuperEntityClass("com.wang.sso.core.support.BaseModel")
                // 自定义实体，公共字段
                .setSuperEntityColumns("id")
                .setTableFillList(tableFillList)
                // 自定义 mapper 父类
                .setSuperMapperClass("com.wang.sso.core.support.BaseDao")
                // 自定义 controller 父类
                .setSuperControllerClass("com.wang.sso.core.support.BaseController")
                // 自定义 service 实现类父类
                // .setSuperServiceImplClass("com.wang.sso.core.support.impl.BaseServiceImpl")
                // 自定义 service 接口父类
                .setSuperServiceClass("com.wang.sso.core.support.BaseService")
                // 【实体】是否生成字段常量（默认 false）
                .setEntityColumnConstant(true)
                // 【实体】是否为构建者模型（默认 false）
                .setEntityBuilderModel(false)
                // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                .setEntityLombokModel(false)
                // Boolean类型字段是否移除is前缀处理
                .setEntityBooleanColumnRemoveIsPrefix(true)
                .setRestControllerStyle(false)
            // .setControllerMappingHyphenStyle(true)
        ).setPackageInfo(
            // 包配置
            PackageConfig()
                .setParent("com.wang.sso.modules")
                .setController("web")
                .setEntity("entity")
                .setMapper("dao")
                .setService("service")
                .setServiceImpl("service.impl")
        ).setCfg(
            // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
            object : InjectionConfig() {
                override fun initMap() {
                    val map = HashMap<String, Any>()
                    this.map = map
                }
            }.setFileOutConfigList(Collections.singletonList(object : FileOutConfig("/templates/mapper.xml.vm") {
                override fun outputFile(tableInfo: TableInfo?): String {
                    return "/develop/code/xml/" + tableInfo?.entityName + ".xml"
                }
            } as FileOutConfig))
        ).setTemplate(
            // 关闭默认 xml 生成，调整生成 至 根目录
            TemplateConfig().setXml(null)
            // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
            // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
            // .setController("...");
            // .setEntity("...");
            // .setMapper("...");
            // .setXml("...");
            // .setService("...");
            // .setServiceImpl("...");
        )
        mpg.execute()
    }
}
