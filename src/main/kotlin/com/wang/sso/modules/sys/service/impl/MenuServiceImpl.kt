package com.wang.sso.modules.sys.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.wang.sso.common.utils.TreeUtils
import com.wang.sso.core.exception.ExceptionEnum
import com.wang.sso.core.exception.ServiceException
import com.wang.sso.modules.sys.dao.IMenuDao
import com.wang.sso.modules.sys.entity.Menu
import com.wang.sso.modules.sys.service.MenuService
import com.wang.sso.modules.sys.utils.UserUtils
import com.wang.sso.modules.sys.vo.MenuTree
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 菜单service实现类
 * @author FlowersPlants
 * @since v1
 */
@Service
open class MenuServiceImpl : MenuService {

    @Autowired
    private lateinit var menuDao: IMenuDao

    private fun getUserMenuTree(roleIds: List<String?>?): MutableList<MenuTree> {
        val menus = menuDao.findListByRoleIds(roleIds)
        return menus.map {
            val tree = MenuTree()
            BeanUtils.copyProperties(it, tree)
            tree
        } as MutableList<MenuTree>
    }

    /**
     * 此处逻辑有点复杂，需要时间
     */
    override fun getUserMenuTree(): MutableList<MenuTree> {
        var menu = UserUtils.getCurrentUserMenuTree()
        if (menu == null || menu.isEmpty()) {
            val roleIds = UserUtils.getCurrentUserRoles()?.map { it.id }
            menu = getUserMenuTree(roleIds)
            if (menu.isEmpty()) {
                return mutableListOf()
            } else {
                // 怎么做？保存到session中？
            }
        }

        // 广度优先建树
        menu = TreeUtils.findBFS(menu) {
            false // 目前设置为false
        }

        // 排序
        menu.forEach {
            it.sortChildren(null)
        }

        return menu
    }

    override fun findList(entity: Menu): MutableList<Menu> {
        return menuDao.selectList(QueryWrapper<Menu>().apply {

        })
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
            val i = menuDao.updateById(entity)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_UPDATE)
            }
        }
    }

    @Transactional
    override fun delete(entity: Menu?) {
        if (entity != null) {
            val i = menuDao.deleteById(entity.id!!)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_DELETE)
            }
        }
    }
}