package com.wang.sso.common.utils

import com.alibaba.fastjson.JSON
import com.baomidou.mybatisplus.core.toolkit.StringUtils
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.wang.sso.core.exception.SsoException
import java.time.format.DateTimeFormatter
import java.time.LocalTime
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


/**
 * jackson json工具类
 * @author FlowersPlants
 * @since v1
 */
object JsonUtils {
    private var objectMapper: ObjectMapper

    init {
        objectMapper = initObjectMapper(ObjectMapper())
    }

    private fun getObjectMapper(): ObjectMapper {
        return this.objectMapper
    }

    /**
     * 对象转json
     */
    fun toJson(obj: Any?): String {
        if (isCharSequence(obj)) {
            return obj as String
        }
        return try {
            getObjectMapper().writeValueAsString(obj)
        } catch (e: JsonProcessingException) {
            throw SsoException(710, "json转化错误: " + e.message)
        }
    }

    /**
     * fastjson方式解析json，解析失败返回null
     */
    fun parse(str: String): Any? {
        return try {
            JSON.parse(str)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Json转换为对象 转换失败返回null
     * @param json json格式字符串
     * @param clazz Java class对象
     * @param <T> class对象的范型
     * @return T
     */
    fun <T> readValue(json: String, clazz: Class<T>): T? {
        var t: T? = null
        try {
            t = getObjectMapper().readValue(json, clazz)
        } catch (ignored: Exception) {
        }

        return t
    }

    /**
     * Json转换为对象 转换失败返回null
     * @param json json格式字符串
     * @param valueTypeRef
     * @param <T>
     * @return T
     */
    fun <T> readValue(json: String, valueTypeRef: TypeReference<T>): T? {
        var t: T? = null
        try {
            t = getObjectMapper().readValue(json, valueTypeRef)
        } catch (ignored: Exception) {
        }

        return t
    }

    /**
     * 初始化objectMapper对象
     */
    private fun initObjectMapper(objectMapper: ObjectMapper): ObjectMapper {
        if (Objects.isNull(objectMapper)) {
            this.objectMapper = ObjectMapper()
        }
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        //不显示为null的字段
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        // 忽略不能转移的字符
        objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true)
        // 过滤对象的null属性.
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        //忽略transient
        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true)
        val simpleModule = SimpleModule()
        simpleModule.addSerializer(
            LocalDateTime::class.java,
            LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        )
        simpleModule.addSerializer(
            LocalDate::class.java,
            LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        )
        simpleModule.addSerializer(LocalTime::class.java, LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")))
        simpleModule.addSerializer(Long::class.java, ToStringSerializer.instance)
        objectMapper.registerModule(simpleModule)
        return objectMapper
    }

    /**
     * 判断是否CharSequence对象
     */
    private fun isCharSequence(obj: Any?): Boolean {
        return Objects.nonNull(obj) && StringUtils.isCharSequence(obj!!::class.java)
    }
}