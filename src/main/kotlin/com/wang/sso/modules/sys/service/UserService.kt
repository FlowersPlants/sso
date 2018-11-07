package com.wang.sso.modules.sys.service

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.wang.sso.core.support.BaseService
import com.wang.sso.modules.sys.entity.User

/**
 * 用户相关接口
 */
interface UserService : BaseService<User> {
    /**
     * 分页方法
     */
    fun findPage(user: User, page: Page<User>): IPage<User>
}