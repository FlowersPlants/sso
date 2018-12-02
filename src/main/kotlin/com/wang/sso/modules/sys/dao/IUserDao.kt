package com.wang.sso.modules.sys.dao

import com.wang.sso.core.mybatis.annotation.MyBatisDao
import com.wang.sso.core.support.BaseDao
import com.wang.sso.modules.sys.entity.User

/**
 * 用户管理数据访问接口，可调用xml配置方法，可使用Select注解
 * @author FlowersPlants
 * @since v1
 */
@MyBatisDao(value = "userDao", entity = User::class)
interface IUserDao : BaseDao<User> {

    /**
     * 根据账号获取user信息
     * @param account 用户账号
     */
    fun findUserByAccount(account: String): User?

    /**
     * 物理删除；对象的属性没有值时此方法不会删除任何东西
     */
    fun physicalDelete(user: User): Int?
}