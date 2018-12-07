package com.wang.sso.modules.sys.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.wang.sso.common.utils.TreeUtils
import com.wang.sso.core.exception.ExceptionEnum
import com.wang.sso.core.exception.ServiceException
import com.wang.sso.modules.sys.dao.IMenuDao
import com.wang.sso.modules.sys.dto.MenuTree
import com.wang.sso.modules.sys.entity.Menu
import com.wang.sso.modules.sys.service.MenuService
import com.wang.sso.modules.sys.utils.UserUtils
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

    @Autowired
    private lateinit var menuDao: IMenuDao

    /**
     * 获取当前用户的菜单并建树结构
     */
    private fun getMenuTree(): MutableList<MenuTree> {
        val menuList = UserUtils.findMenuList() ?: mutableListOf()

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

    override fun getUserMenuTree(): MutableList<MenuTree> {
        return getMenuTree()
    }

    /**
     * 菜单功能里不实现此方法
     */
    override fun findPage(entity: Menu?, page: Page<Menu>): IPage<Menu>? {
        return null
    }

    override fun findList(entity: Menu?): MutableList<Menu>? {
        return menuDao.selectList(QueryWrapper<Menu>().apply {
            if (entity != null) {
                if (!entity.id.isNullOrEmpty()) {
                    eq("id", "${entity.id}")
                }
                if (!entity.name.isNullOrEmpty()) {
                    like("name", "${entity.name}")
                }
                orderByAsc("sort")
            }
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