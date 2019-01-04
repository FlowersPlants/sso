package com.wang.sso.core.support

/**
 * 不继承mybatis plus插件的BaseService接口，因为里面的方法太多
 * 很多方法用不到，所以在需要的时候自定义即可
 * 可在此接口定义几个通用方法
 *
 * @author FlowersPlants
 * @since v1
 */
interface BaseService<T : BaseModel> {
    /**
     * 保存方法，如果ID为null则为新增，否则为修改
     */
    fun save(entity: T?)

    /**
     * 根据ID删除
     */
    fun deleteById(id: String?)
}