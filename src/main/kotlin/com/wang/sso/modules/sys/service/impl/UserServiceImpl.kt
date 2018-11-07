package com.wang.sso.modules.sys.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
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

    override fun findPage(user: User, page: Page<User>): IPage<User> {
        return userDao.selectPage(page, QueryWrapper<User>().apply {
            like("account", "${user.account}")
            orderByDesc("create_at")
        })
    }

    @Log("获取用户列表")
    override fun findList(entity: User): MutableList<User> {
        return userDao.selectList(QueryWrapper<User>().apply {
            eq("status", "0") // 设置查询"0"状态下的所有用户
        })
    }

    @Transactional
    override fun insert(entity: User?) {
        if (entity != null) {
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
            val i = userDao.updateById(entity)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_UPDATE)
            }
        }
    }

    @Transactional
    override fun delete(entity: User?) {
        if (entity != null) {
            val i = userDao.deleteById(entity.id!!)
            if (i <= 0) {
                throw ServiceException(703, "删除失败")
            }
        }
    }
}