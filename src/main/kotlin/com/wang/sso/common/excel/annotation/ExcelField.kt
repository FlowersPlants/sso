package com.wang.sso.common.excel.annotation

/**
 * excel导入导出注解
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ExcelField(
    val attrName: String,
    val title: String = "", // 导出字段标题
    val type: Int = 0, // 字段类型（0：导出导入；1：仅导出；2：仅导入）
    val align: Int = 0, // 导出字段对齐方式（0：自动；1：靠左；2：居中；3：靠右）
    val sort: Int = 0,
    val dictType: String = "" // 如果是字典类型，设置字典的type值
)