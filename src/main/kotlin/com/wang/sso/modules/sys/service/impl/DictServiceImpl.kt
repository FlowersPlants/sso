package com.wang.sso.modules.sys.service.impl

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.wang.sso.core.cache.redis.RedisService
import com.wang.sso.core.consts.CommonConstant
import com.wang.sso.core.exception.ExceptionEnum
import com.wang.sso.core.exception.ServiceException
import com.wang.sso.modules.sys.dao.IDictDao
import com.wang.sso.modules.sys.entity.Dict
import com.wang.sso.modules.sys.service.DictService
import com.wang.sso.modules.sys.utils.DictUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DictServiceImpl : DictService {
    @Autowired
    private lateinit var dictDao: IDictDao

    @Autowired
    private lateinit var redisService: RedisService

    override fun findPage(dict: Dict?, page: Page<Dict>): IPage<Dict>? {
        return page.apply {
            val dictList = DictUtils.getDictList(dict?.type) ?: mutableListOf()
            this.total = dictList.size.toLong()
            this.records = dictList
        }
    }

    override fun save(entity: Dict?) {
        if (entity != null) {
            val enum: ExceptionEnum?
            val i = if (entity.id.isNullOrEmpty()) {
                enum = ExceptionEnum.SERVICE_INSERT
                dictDao.insert(entity)
            } else {
                enum = ExceptionEnum.SERVICE_UPDATE
                dictDao.updateById(entity)
            }
            if (i <= 0) {
                throw ServiceException(enum)
            }

            redisService.del(CommonConstant.CACHE_DICT)
        }
    }

    override fun deleteById(id: String?) {
        if (id != null) {
            val i = dictDao.deleteById(id)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_DELETE)
            }

            redisService.del(CommonConstant.CACHE_DICT)
        }
    }
}
