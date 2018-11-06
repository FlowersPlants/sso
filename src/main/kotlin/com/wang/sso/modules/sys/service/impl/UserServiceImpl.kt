package com.wang.sso.modules.sys.service.impl

import com.wang.sso.core.annotations.Log
import com.wang.sso.core.exception.ExceptionEnum
import com.wang.sso.core.exception.ServiceException
import com.wang.sso.core.exception.SsoException
import com.wang.sso.modules.sys.dao.IUserDao
import com.wang.sso.modules.sys.entity.User
import com.wang.sso.modules.sys.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class UserServiceImpl : UserService {

    @Autowired
    private lateinit var userDao: IUserDao

    @Log("获取用户列表")
    override fun findList(entity: User): MutableList<User> {
        entity.status = "0" // 设置查询"0"状态下的所有用户
        return userDao.findList(entity)
    }

    @Transactional
    override fun insert(entity: User?) {
        if (entity != null) {
            entity.preInsert()
            val i = userDao.insert(entity)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_INSERT)
            }
        }
        throw SsoException(501, "$entity", "不能为null")
    }

    @Transactional
    override fun update(entity: User?) {
        if (entity != null) {
            entity.preUpdate()
            val i = userDao.update(entity)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_UPDATE)
            }
        }
    }

    @Transactional
    override fun delete(entity: User?) {
        if (entity != null) {
            entity.preLogicDelete()
            val i = userDao.delete(entity.id!!)
            if (i <= 0) {
                throw ServiceException(703, "删除失败")
            }
        }
    }
}