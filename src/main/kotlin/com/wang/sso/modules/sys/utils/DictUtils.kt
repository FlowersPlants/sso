package com.wang.sso.modules.sys.utils

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.wang.sso.common.utils.SpringUtils
import com.wang.sso.core.cache.redis.RedisService
import com.wang.sso.core.consts.CommonConstant
import com.wang.sso.modules.sys.dao.IDictDao
import com.wang.sso.modules.sys.entity.Dict

/**
 * 字典工具类
 * @author FlowersPlants
 * @date 2019-01-04
 */
object DictUtils {
    private val dictDao = SpringUtils.getBean(IDictDao::class.java)

    // redis操作接口
    private val redisService = SpringUtils.getBean(RedisService::class.java)

    @Suppress("UNCHECKED_CAST")
    fun getDictList(type: String?): MutableList<Dict>? {
        var dictList: MutableList<Dict>? = redisService.get(CommonConstant.CACHE_DICT) as? MutableList<Dict>
        if (dictList == null) {
            dictList = findList(type)
            if (dictList == null) {
                return null
            }
            redisService.set(CommonConstant.CACHE_DICT, dictList)
        }
        return if (type != null && !type.isNullOrEmpty()) {
            dictList.filter { it.type == type } as MutableList<Dict>
        } else {
            dictList
        }
    }

    private fun findList(type: String?): MutableList<Dict>? {
        return dictDao.selectList(QueryWrapper<Dict>().apply {
            if (type != null && !type.isNullOrEmpty()) {
                eq("type", "$type")
            }
            orderByAsc("sort")
        })
    }
}
