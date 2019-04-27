package com.wang.sso.modules.sys.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.wang.sso.core.cache.redis.RedisService
import com.wang.sso.core.consts.CommonConstant
import com.wang.sso.core.exception.ExceptionEnum
import com.wang.sso.core.exception.ServiceException
import com.wang.sso.modules.sys.dao.IRoleDao
import com.wang.sso.modules.sys.dto.RoleDto
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

    @Autowired
    private lateinit var redisService: RedisService

    override fun findById(id: Serializable?): Role? {
        return if (id != null) roleDao.selectById(id) else null
    }

    override fun findPage(entity: Role?, page: Page<Role>): IPage<Role>? {
        return roleDao.selectPage(page, QueryWrapper<Role>().apply {
            if (entity != null) {
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

    @Transactional
    override fun save(entity: Role?) {
        if (entity != null) {
            val enum: ExceptionEnum?
            val i = if (entity.id.isNullOrEmpty()) {
                enum = ExceptionEnum.SERVICE_INSERT
                roleDao.insert(entity)
            } else {
                enum = ExceptionEnum.SERVICE_UPDATE
                roleDao.updateById(entity)
            }
            if (i <= 0) {
                throw ServiceException(enum)
            }

            redisService.del(CommonConstant.CACHE_ROLES)
        }
    }

    @Transactional
    override fun insertBatchRecord(roleDto: RoleDto?) {
        if (roleDto != null) {
            if (roleDto.id != null && !roleDto.id.isNullOrEmpty()) {
                if (roleDto.menuIds != null) {
                    roleDao.deleteRoleMenuByRoleId(roleDto)
                } else if (roleDto.userIds != null) {
                    roleDao.deleteRoleUserByRoleId(roleDto)
                }
            }

            if (roleDto.menuIds != null) {
                val i = roleDao.insertBatchMenuRecord(roleDto)
                if (i != roleDto.menuIds?.size) {
                    throw ServiceException(ExceptionEnum.SERVICE_INSERT)
                }
            } else if (roleDto.userIds != null) {
                val i = roleDao.insertBatchUserRecord(roleDto)
                if (i != roleDto.userIds?.size) {
                    throw ServiceException(ExceptionEnum.SERVICE_INSERT)
                }
            }

            redisService.del(CommonConstant.CACHE_ROLES)
        }
    }

    /**
     * 注意以下情景：
     * 1、某个角色有活动用户时（状态为no delete）不能删除
     * 2、某个角色无有效用户时需删除中间表记录
     */
    @Transactional
    override fun deleteById(id: String?) {
        if (id != null) {
            val i = roleDao.deleteById(id)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_DELETE)
            }

            redisService.del(CommonConstant.CACHE_ROLES)
        }
    }
}
