package com.wang.sso.modules.sys.service.impl

import com.wang.sso.core.exception.ExceptionEnum
import com.wang.sso.core.exception.ServiceException
import com.wang.sso.modules.sys.dao.IRoleDao
import com.wang.sso.modules.sys.entity.Role
import com.wang.sso.modules.sys.service.RoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class RoleServiceImpl : RoleService {

    @Autowired
    private lateinit var roleDao: IRoleDao

    override fun findList(entity: Role): MutableList<Role> {
        return roleDao.selectList(null)
    }

    @Transactional
    override fun insert(entity: Role?) {
        if (entity != null) {
            val i = roleDao.insert(entity)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_INSERT)
            }
        }
    }

    @Transactional
    override fun update(entity: Role?) {
        if (entity != null) {
            val i = roleDao.updateById(entity)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_UPDATE)
            }
        }
    }

    @Transactional
    override fun delete(entity: Role?) {
        if (entity != null) {
            val i = roleDao.deleteById(entity.id!!)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_DELETE)
            }
        }
    }
}