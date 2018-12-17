package com.wang.sso.modules.sys.service

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.wang.sso.core.support.BaseService
import com.wang.sso.modules.sys.entity.User

/**
 * 用户相关接口
 * @author FlowersPlants
 * @since v1
 */
interface UserService : BaseService<User> {
    fun findPage(entity: User?, page: Page<User>?): IPage<User>?

    fun findList(entity: User?): MutableList<User>?
}