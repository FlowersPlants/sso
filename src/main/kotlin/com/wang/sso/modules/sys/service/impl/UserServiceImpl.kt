package com.wang.sso.modules.sys.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.wang.sso.core.cache.redis.RedisService
import com.wang.sso.core.consts.CommonConstant
import com.wang.sso.core.exception.ExceptionEnum
import com.wang.sso.core.exception.ServiceException
import com.wang.sso.core.exception.SsoException
import com.wang.sso.modules.sys.dao.IUserDao
import com.wang.sso.modules.sys.entity.User
import com.wang.sso.modules.sys.service.UserService
import com.wang.sso.modules.sys.utils.UserUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable

/**
 * 用户service实现类
 * @author FlowersPlants
 * @since v1
 */
@Service
open class UserServiceImpl : UserService {
    companion object {
        val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()
    }

    @Autowired
    private lateinit var userDao: IUserDao

    @Autowired
    private lateinit var redisService: RedisService

    override fun findPage(entity: User?, page: Page<User>?): IPage<User> {
        return userDao.selectPage(page, QueryWrapper<User>().apply {
            if (entity != null) {
                if (!entity.id.isNullOrEmpty()) {
                    eq("id", "${entity.id}")
                }
                if (!entity.account.isNullOrEmpty()) {
                    eq("account", "${entity.account}")
                }
                if (!entity.name.isNullOrEmpty()) {
                    like("name", "${entity.name}")
                }
            }
            orderByAsc("sort")
            orderByDesc("create_at")
        })
    }

    override fun findList(entity: User?): MutableList<User>? {
        return userDao.selectList(null)
    }

    /**
     * 新增用户时应该在注册之前异步验证账号是否已存在
     */
    @Transactional
    override fun insert(entity: User?) {
        if (entity != null) {
            if (entity.password.isNullOrEmpty()) {
                entity.password = passwordEncoder.encode(CommonConstant.DEFAULT_PASSWORD)
            }
            val i = userDao.insert(entity)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_INSERT)
            }

            redisService.hset(CommonConstant.CACHE_USERS, CommonConstant.USER_CACHE_ID + entity.id, entity)
            // redisService.hset(CommonConstant.CACHE_USERS, CommonConstant.USER_CACHE_ACCOUNT + entity.account, entity)
        } else throw SsoException(501, "$entity", "不能为null")
    }

    @Transactional
    override fun update(entity: User?) {
        if (entity != null) {
            val i = userDao.updateById(entity)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_UPDATE)
            }

            UserUtils.clearCache(entity)
            redisService.hset(CommonConstant.CACHE_USERS, CommonConstant.USER_CACHE_ID + entity.id, entity)
        }
    }

    @Transactional
    override fun delete(entity: User?) {
        if (entity != null) {
            val i = userDao.deleteById(entity.id!!)
            if (i <= 0) {
                throw ServiceException(703, "删除失败")
            }

            UserUtils.clearCache(entity)
        }
    }

    @Transactional
    override fun deleteById(id: Serializable?) {
        if (id != null) {
            val i = userDao.deleteById(id)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_DELETE)
            }

            redisService.hdel(CommonConstant.CACHE_USERS, CommonConstant.USER_CACHE_ID + id)
        }
    }
}