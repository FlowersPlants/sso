package com.wang.sso.modules.sys.entity

import com.baomidou.mybatisplus.annotation.TableName
import com.wang.sso.core.support.BaseModel

/**
 * 字典实体
 */
@TableName("sys_dict")
class Dict : BaseModel() {
    companion object {
        private const val serialVersionUID = 1L
    }

    /**
     * 数据值
     */
    var value: String? = null

    /**
     * 标签名
     */
    var label: String? = null

    /**
     * 类型
     */
    var type: String? = null

    /**
     * 排序号
     */
    var sort: Int? = null

    /**
     * 描述
     */
    var description: String? = null
}
