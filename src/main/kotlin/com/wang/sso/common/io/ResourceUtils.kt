package com.wang.sso.common.io

import com.wang.sso.core.exception.SsoException
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.util.ResourceUtils
import java.io.IOException
import java.io.InputStream

/**
 * 资源工具类
 * @author FlowersPlants
 * @since v1
 */
object ResourceUtils : ResourceUtils() {
    private val resourceLoader = DefaultResourceLoader()

    /**
     * 获取资源加载器（可读取jar内的文件）
     * @author ThinkGem
     */
    fun getResourceLoader(): ResourceLoader {
        return resourceLoader
    }

    /**
     * 获取ClassLoader
     */
    fun getClassLoader(): ClassLoader? {
        return resourceLoader.classLoader
    }

    /**
     * 获取资源加载器（可读取jar内的文件）
     */
    fun getResource(location: String): Resource {
        return resourceLoader.getResource(location)
    }

    /**
     * 获取资源文件流（用后记得关闭）
     * @param location
     * @throws IOException
     */
    @Throws(IOException::class)
    fun getResourceFileStream(location: String): InputStream {
        val resource = resourceLoader.getResource(location)
        return resource.inputStream
    }

    /**
     * 获取资源文件内容
     * @param location
     */
    fun getResourceFileContent(location: String): String {
        var `is`: InputStream? = null
        try {
            `is` = com.wang.sso.common.io.ResourceUtils.getResourceFileStream(location)
            return `is`.toString()
        } catch (e: IOException) {
            throw SsoException(500, e.message!!)
        } finally {
            try {
                `is`?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * Spring 搜索资源文件
     * @param locationPattern
     */
    fun getResources(locationPattern: String): Array<Resource> {
        try {
            return PathMatchingResourcePatternResolver().getResources(locationPattern)
        } catch (e: IOException) {
            throw SsoException(500, e.message!!)
        }

    }
}