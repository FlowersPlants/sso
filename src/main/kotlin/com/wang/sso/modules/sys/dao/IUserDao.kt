package com.wang.sso.modules.sys.dao

import com.wang.sso.core.annotations.MyBatisDao
import com.wang.sso.core.support.BaseDao
import com.wang.sso.modules.sys.entity.User

/**
 * 用户管理数据访问接口，可调用xml配置方法，可使用 @see Select注解
 */
@MyBatisDao(value = "userDao", entity = User::class)
interface IUserDao : BaseDao<User> {
    fun findUserByUsername(username: String): User?
}