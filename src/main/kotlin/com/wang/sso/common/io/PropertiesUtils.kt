package com.wang.sso.common.io

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.regex.Pattern

/**
 * Properties工具类， 可载入多个properties、yml文件，
 * 相同的属性在最后载入的文件中的值将会覆盖之前的值，
 * 取不到从System.getProperty()获取。
 */
class PropertiesUtils {
    companion object {
        // 默认加载的文件，可通过继承覆盖（若有相同Key，优先加载后面的）
        val DEFAULT_CONFIG_FILE = arrayOf("classpath:config/application.yml", "classpath:application.yml")

        private val logger = LoggerFactory.getLogger(PropertiesUtils::class.java)

        // 正则表达式预编译
        private val p1 = Pattern.compile("\\$\\{.*?\\}")

        /**
         * 重新加载实例（重新实例化，以重新加载属性文件数据）
         */
        fun releadInstance() {
            PropertiesLoaderHolder.reloadInstance()
        }

        /**
         * 当前类实例
         */
        fun getInstance(): PropertiesUtils {
            return PropertiesLoaderHolder.INSTANCE!!
        }
    }

    private val properties = Properties()

    /**
     * 获取当前加载的属性
     */
    fun getProperties(): Properties {
        return properties
    }

    /**
     * 当前类的实例持有者（静态内部类，延迟加载，懒汉式，线程安全的单例模式）
     */
    private object PropertiesLoaderHolder {
        var INSTANCE: PropertiesUtils? = null

        init {
            releadInstance()
        }

        fun reloadInstance() {
            val configFiles = HashSet<String>()
            val resources = ResourceUtils.getResources("classpath*:/config/sso-*.*")
            for (resource in resources) {
                configFiles.add("classpath:/config/" + resource.filename!!)
            }
            for (configFile in DEFAULT_CONFIG_FILE) {
                configFiles.add(configFile)
            }
            logger.debug("Loading app config: {}", configFiles)
            INSTANCE = PropertiesUtils(*configFiles.toTypedArray())
        }
    }

    constructor(vararg configFiles: String) {
        for (location in configFiles) {
            try {
                val resource = ResourceUtils.getResource(location)
                if (resource.exists()) {
                    val ext = FileUtils.getFileExtension(location)
                    if ("properties" == ext) {
                        var `is`: InputStreamReader? = null
                        try {
                            `is` = InputStreamReader(resource.inputStream, StandardCharsets.UTF_8)
                            properties.load(`is`)
                        } catch (ex: IOException) {
                            logger.error("Load $location failure. ", ex)
                        } finally {
                            try {
                                `is`?.close()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }

                        }
                    } else if ("yml" == ext) {
                        val bean = YamlPropertiesFactoryBean()
                        bean.setResources(resource)
                        for ((key, value) in bean.getObject()!!) {
                            properties[key ?: ""] = value
                        }
                    }
                }
            } catch (e: Exception) {
                logger.error("Load $location failure. ", e)
            }

        }
    }

    /**
     * 获取属性值，取不到从System.getProperty()获取，都取不到返回null
     */
    fun getProperty(key: String): String? {
        var value: String? = properties.getProperty(key)
        if (value != null) {
            // 支持嵌套取值的问题  key=${xx}/yy
            val m = p1.matcher(value)
            while (m.find()) {
                val g = m.group()
                val keyChild = g.replace("\\$\\{".toRegex(), "").replace("\\}".toRegex(), "")
                value = value!!.replace(g, getProperty(keyChild)!!)
            }
            return value
        } else {
            val systemProperty = System.getProperty(key)
            if (systemProperty != null) {
                return systemProperty
            }
        }
        return null
    }

    /**
     * 取出String类型的Property，但以System的Property优先，如果都为null则返回defaultValue值
     */
    fun getProperty(key: String, defaultValue: String): String {
        val value = getProperty(key)
        return value ?: defaultValue
    }
}