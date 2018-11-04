package com.wang.sso.modules.sys.dao

import com.wang.sso.core.annotations.MyBatisDao
import com.wang.sso.core.support.BaseDao
import com.wang.sso.modules.sys.entity.Role
import org.apache.ibatis.annotations.Select

@MyBatisDao(value = "roleDao", entity = Role::class)
interface IRoleDao : BaseDao<Role> {

    @Select("select a.* " +
            "from sys_role a " +
            "left join sys_role_user ru on ru.role_id=a.id " +
            "left join sys_user u on u.id=ru.user_id " +
            "where u.id=#{userId}")
    fun findByUserId(userId: String?): MutableList<Role>
}