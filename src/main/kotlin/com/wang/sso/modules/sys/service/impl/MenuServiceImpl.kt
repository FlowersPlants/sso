package com.wang.sso.modules.sys.service.impl

import com.wang.sso.common.utils.TreeUtils
import com.wang.sso.core.cache.redis.RedisService
import com.wang.sso.core.consts.CommonConstant
import com.wang.sso.core.exception.ExceptionEnum
import com.wang.sso.core.exception.ServiceException
import com.wang.sso.modules.sys.dao.IMenuDao
import com.wang.sso.modules.sys.dto.MenuTree
import com.wang.sso.modules.sys.entity.Menu
import com.wang.sso.modules.sys.entity.User
import com.wang.sso.modules.sys.service.MenuService
import com.wang.sso.modules.sys.utils.UserUtils
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

    @Autowired
    private lateinit var redisService: RedisService

    /**
     * 获取菜单并构建树结构
     * 有如下问题：
     * 1、当用户只有几个二级菜单时，没有一个菜单的父ID时0，导致建树失败
     * 2、
     *
     * 解决问题思路：
     * 1、首先利用广度优先方法构建，然后再调用两层循环方法（需要获取用户菜单的所有父菜单，直到根节点）
     */
    private fun getMenu(user: User?): MutableList<MenuTree> {
        val menuList = UserUtils.findMenuList(user?.id) ?: mutableListOf()

        var menuTree = menuList.map {
            val tree = MenuTree()
            BeanUtils.copyProperties(it, tree)
            tree
        } as MutableList<MenuTree>

        // 两层循环建树，会对每个node都建一棵树（广度优先好像有点问题）
        TreeUtils.build(menuTree, CommonConstant.DEFAULT_ROOT_MENU_ID)

        // 去掉多余的node
        if (menuTree.isNotEmpty()) {
            val root = menuTree.find {
                it.parentId == CommonConstant.DEFAULT_ROOT_MENU_ID
            } // 只返回有一个根节点，所以只能有一个根节点
            if (user == null) { // 获取所有菜单时
                menuTree = mutableListOf()
                menuTree.add(root!!)
            } else {
                menuTree = mutableListOf()
                root?.children?.forEach {
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

    override fun getMenuTree(): MutableList<MenuTree> {
        return getMenu(null) // 获取所有菜单
    }

    override fun getByRole(id: String?): MutableList<Menu>? {
        return if (id != null && !id.isNullOrEmpty()) {
            menuDao.findByRoleIds(mutableListOf(id))
        } else {
            null
        }
    }

    @Transactional
    override fun save(entity: Menu?) {
        if (entity != null) {
            val enum: ExceptionEnum?
            val i = if (entity.id.isNullOrEmpty()) {
                enum = ExceptionEnum.SERVICE_INSERT
                menuDao.insert(entity)
            } else {
                enum = ExceptionEnum.SERVICE_UPDATE
                menuDao.updateById(entity)
            }
            if (i <= 0) {
                throw ServiceException(enum)
            }

            redisService.del(CommonConstant.CACHE_MENUS)
        }
    }

    /**
     * 删除接口，删除此菜单和其所有子菜单
     */
    @Transactional
    override fun deleteById(id: String?) {
        if (id != null) {
            val ids = menuDao.findChildrenById(id)!!.map { it.id }
            val i = menuDao.deleteBatchIds(ids)
            if (i <= 0) {
                throw ServiceException(ExceptionEnum.SERVICE_DELETE)
            }

            redisService.del(CommonConstant.CACHE_MENUS)
        }
    }
}
