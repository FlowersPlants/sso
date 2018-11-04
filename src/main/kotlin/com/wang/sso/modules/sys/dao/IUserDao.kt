package com.wang.sso.modules.sys.dao

import com.wang.sso.core.annotations.MyBatisDao
import com.wang.sso.core.support.BaseDao
import com.wang.sso.modules.sys.entity.User
import org.apache.ibatis.annotations.Select

@MyBatisDao(value = "userDao", entity = User::class)
interface IUserDao : BaseDao<User> {
    @Select("select * from sys_user where account=#{username}")
    fun findUserByUsername(username: String): User?
}