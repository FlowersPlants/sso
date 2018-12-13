package com.wang.sso.modules.sys.service.impl

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.wang.sso.common.utils.TreeUtils
import com.wang.sso.core.exception.ExceptionEnum
import com.wang.sso.core.exception.ServiceException
import com.wang.sso.modules.sys.dao.IMenuDao
import com.wang.sso.modules.sys.dto.MenuTree
import com.wang.sso.modules.sys.entity.Menu
import com.wang.sso.modules.sys.entity.User
import com.wang.sso.modules.sys.service.MenuService
import com.wang.sso.modules.sys.utils.UserUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable

/**
 * 菜单service实现类
 * @author FlowersPlants
 * @since v1
 */
@Service
open class MenuServiceImpl : MenuService {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var menuDao: IMenuDao

    /**
     * 获取当前用户的菜单并建树结构
     */
    private fun getMenu(user: User?): MutableList<MenuTree> {
        val menuList = UserUtils.findMenuList(user?.id) ?: mutableListOf()

        var menuTree = menuList.map {
            val tree = MenuTree()
            BeanUtils.copyProperties(it, tree)
            tree
        } as MutableList<MenuTree>

        // 两层循环建树，会对每个node都建一棵树（广度优先好像有点问题）
        TreeUtils.build(menuTree, "0")

        // 去掉多余的node
        if (menuTree.isNotEmpty()) {
            val root = menuTree[0]
            if (root.children != null && root.children!!.isNotEmpty()) {
                menuTree = mutableListOf()
                root.children?.forEach {
                    menuTree.add(it as MenuTree)
                }
            }
        }

        // 排序
        menuTree.sortedWith(Comparator { o1, o2 -> o1.sort!! - o2.sort!! })
        menuTree.forEach {
            it.sortChildren(null)
        }

        return menuTree
    }

    /**
     * 获取当前用户的所有菜单
     * 该用户的菜单建树时可能不完整（没有包含所有父菜单）
     */
    override fun getUserMenuTree(): MutableList<MenuTree> {
        return getMenu(UserUtils.getCurrentUser()) // 获取当前用户的菜单
    }

    /**
     * 菜单功能里不实现此方法
     */
    override fun findPage(entity: Menu?, page: Page<Menu>): IPage<Menu>? {
        logger.info("findPage方法为空实现")
        return null
    }

    override fun findList(entity: Menu?): MutableList<Menu>? {
        logger.info("findList方法空实现")
        return null
    }

    override fun getMenuTree(): MutableList<MenuTree> {
        return getMenu(null) // 获取所有菜单
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

    @Transactional
    override fun deleteById(id: Serializable?) {
        if (id != null) {
            val i = menuDao.deleteById(id)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_DELETE)
            }
        }
    }
}