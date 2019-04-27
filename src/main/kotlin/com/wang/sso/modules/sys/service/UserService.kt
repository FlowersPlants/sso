package com.wang.sso.modules.sys.service

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.wang.sso.core.support.BaseService
import com.wang.sso.modules.sys.entity.User
import java.io.Serializable

/**
 * 用户相关接口
 * @author FlowersPlants
 * @since v1
 */
interface UserService : BaseService<User> {
    fun findById(id: Serializable?): User?

    fun findPage(entity: User?, page: Page<User>): IPage<User>?

    fun findList(entity: User?): MutableList<User>?
}
