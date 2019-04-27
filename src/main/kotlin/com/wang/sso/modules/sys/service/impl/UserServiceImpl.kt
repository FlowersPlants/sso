package com.wang.sso.modules.sys.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.wang.sso.core.consts.CommonConstant
import com.wang.sso.core.exception.ExceptionEnum
import com.wang.sso.core.exception.ServiceException
import com.wang.sso.core.exception.SsoException
import com.wang.sso.core.logging.annotation.SystemLog
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

    override fun findById(id: Serializable?): User? {
        return if (id != null) userDao.selectById(id) else null
    }

    @SystemLog(title = "获取用户列表")
    override fun findPage(entity: User?, page: Page<User>): IPage<User> {
        return userDao.selectPage(page, QueryWrapper<User>().apply {
            if (entity != null) {
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
    override fun save(entity: User?) {
        if (entity != null) {
            if (entity.password.isNullOrEmpty()) {
                entity.password = CommonConstant.DEFAULT_PASSWORD
            }
            entity.password = passwordEncoder.encode(entity.password)

            val enum: ExceptionEnum?
            val i = if (entity.id.isNullOrEmpty()) {
                enum = ExceptionEnum.SERVICE_INSERT
                userDao.insert(entity)
            } else {
                enum = ExceptionEnum.SERVICE_UPDATE
                userDao.updateById(entity)
            }
            if (i <= 0) {
                throw ServiceException(enum)
            }

            UserUtils.clearCache(entity)
        } else throw SsoException(501, "$entity", "不能为null")
    }

    @Transactional
    override fun deleteById(id: String?) {
        if (id != null) {
            val i = userDao.deleteById(id)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_DELETE)
            }

            UserUtils.clearCache(UserUtils.findUserById(id)!!)
        }
    }
}
