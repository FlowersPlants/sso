package com.wang.sso.common.idgen

import java.security.SecureRandom
import java.util.*

object IdGenerate {

    private val random = SecureRandom()

    /**
     * 生成uuid，中间无"-"分割
     * @return 全局唯一ID
     */
    fun uuid(): String {
        return UUID.randomUUID().toString().replace("-".toRegex(), "")
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    fun randomLong(): Long {
        return Math.abs(random.nextLong())
    }

    /**
     * 获取新代码编号
     */
    fun nextCode(code: String?): String? {
        if (code != null) {
            var str = code.trim { it <= ' ' }
            val len = str.length - 1
            var lastNotNumIndex = 0
            for (i in len downTo 0) {
                if (!(str[i] in '0'..'9')) {
                    lastNotNumIndex = i
                    break
                }
            }
            // 如果最后一位是数字，并且last索引位置还在最后，则代表是纯数字，则最后一个不是数字的索引为-1
            if (str[len] in '0'..'9' && lastNotNumIndex == len) {
                lastNotNumIndex = -1
            }
            val prefix = str.substring(0, lastNotNumIndex + 1)
            val numStr = str.substring(lastNotNumIndex + 1, str.length)
            val num = numStr.toLong()
            str = prefix + (num + 1).toString()
            return str
        }
        return null
    }
}