package com.wang.sso.core.support

import java.io.Serializable

/**
 * 不继承mybatis plus插件的BaseService接口，因为里面的方法太多
 * 很多方法用不到，所以在需要的时候自定义即可
 * 可在此接口定义几个通用方法
 *
 * @author FlowersPlants
 * @since v1
 */
interface BaseService<T : BaseModel> {
    fun insert(entity: T?)

    fun update(entity: T?)

    fun delete(entity: T?)

    fun deleteById(id: Serializable?)
}