package com.wang.sso.modules.sys.service.impl

import com.wang.sso.core.exception.ExceptionEnum
import com.wang.sso.core.exception.ServiceException
import com.wang.sso.modules.sys.dao.IMenuDao
import com.wang.sso.modules.sys.entity.Menu
import com.wang.sso.modules.sys.service.MenuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class MenuServiceImpl : MenuService {

    @Autowired
    private lateinit var menuDao: IMenuDao

    override fun findList(entity: Menu): MutableList<Menu> {
        return menuDao.findList(entity)
    }

    @Transactional
    override fun insert(entity: Menu?) {
        if (entity != null) {
            val i = menuDao.insert(entity)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_INSERT)
            }
        }
    }

    @Transactional
    override fun update(entity: Menu?) {
        if (entity != null) {
            val i = menuDao.update(entity)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_UPDATE)
            }
        }
    }

    @Transactional
    override fun delete(entity: Menu?) {
        if (entity != null) {
            val i = menuDao.delete(entity.id!!)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_DELETE)
            }
        }
    }
}