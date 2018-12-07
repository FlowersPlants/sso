package com.wang.sso.modules.sys.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.wang.sso.core.exception.ExceptionEnum
import com.wang.sso.core.exception.ServiceException
import com.wang.sso.modules.sys.dao.IRoleDao
import com.wang.sso.modules.sys.entity.Role
import com.wang.sso.modules.sys.service.RoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable

/**
 * 角色service实现类
 * @author FlowersPlants
 * @since v1
 */
@Service
open class RoleServiceImpl : RoleService {

    @Autowired
    private lateinit var roleDao: IRoleDao

    override fun findPage(entity: Role?, page: Page<Role>): IPage<Role>? {
        return roleDao.selectPage(page, QueryWrapper<Role>().apply {
            if (entity != null) {
                if (!entity.id.isNullOrEmpty()) {
                    eq("id", "${entity.id}")
                }
                if (!entity.name.isNullOrEmpty()) {
                    like("name", "${entity.name}")
                }
                if (!entity.enname.isNullOrEmpty()) {
                    like("enname", "${entity.enname}")
                }

                orderByAsc("sort")
                orderByDesc("create_at")
            }
        })
    }

    override fun findList(entity: Role?): MutableList<Role>? {
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

    @Transactional
    override fun deleteById(id: Serializable?) {
        if (id != null) {
            val i = roleDao.deleteById(id)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_DELETE)
            }
        }
    }
}