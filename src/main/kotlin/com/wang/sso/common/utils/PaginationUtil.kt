package com.wang.sso.common.utils

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import javax.servlet.http.HttpServletRequest

/**
 * 分页工具类
 * 如果需要分页，必须传入参数：current，pageSize
 */
object PaginationUtil {
    private const val KEY_CURRENT = "current"
    private const val KEY_PAGE_SIZE = "pageSize"

    private fun getCurrent(request: HttpServletRequest): Long? {
        return request.getParameter(KEY_CURRENT).toLong()
    }

    private fun getPageSize(request: HttpServletRequest): Long? {
        return request.getParameter(KEY_PAGE_SIZE)?.toLong()
    }

    fun <T> getRequestPage(request: HttpServletRequest): Page<T>? {
        if (!pageable(request)) {
            return null
        }
        return Page<T>().apply {
            current = getCurrent(request)!!
            size = getPageSize(request)!!
        }
    }

    /**
     * 分页参数是否合法
     */
    private fun pageable(current: Int?, size: Int?): Boolean {
        return current != null && size != null && current > 0 && size > 0
    }

    /**
     * 分页参数是否合法
     */
    private fun pageable(request: HttpServletRequest): Boolean {
        return pageable(getCurrent(request)?.toInt(), getPageSize(request)?.toInt())
    }
}