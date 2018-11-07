package com.wang.sso.common.idgen

import java.util.*

/**
 * id生成策略
 */
object IdGenerate {
    /**
     * 生成uuid，中间无"-"分割
     * @return 全局唯一ID
     */
    fun uuid(): String {
        return UUID.randomUUID().toString().replace("-".toRegex(), "")
    }
}