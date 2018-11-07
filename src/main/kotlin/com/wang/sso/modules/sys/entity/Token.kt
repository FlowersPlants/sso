package com.wang.sso.modules.sys.entity

import com.wang.sso.core.support.BaseModel
import java.util.*

/**
 * 保存到数据库的token
 */
class Token : BaseModel() {
    companion object {
        private const val serialVersionUID = 4566334160572911795L
    }

    /**
     * 过期时间
     */
    var expireTime: Date? = null

    /**
     * LoginUser的json串
     */
    var value: String? = null
}