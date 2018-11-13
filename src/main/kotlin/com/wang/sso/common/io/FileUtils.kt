package com.wang.sso.common.io

/**
 * 文件管理工具类
 * @author FlowersPlants
 * @since v1
 */
object FileUtils {

    /**
     * 获取文件后缀，小写
     */
    fun getFileExtension(location: String?): String? {
        if ((location == null)
                || (location.lastIndexOf(".") == -1)
                || (location.lastIndexOf(".") == location.length - 1)) {
            return null
        }
        return location.substring(location.lastIndexOf(".") + 1).toLowerCase()
    }
}