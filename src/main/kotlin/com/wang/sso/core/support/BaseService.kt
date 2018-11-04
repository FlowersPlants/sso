package com.wang.sso.core.support

interface BaseService<T : BaseModel<T>> {

    fun findList(entity: T): MutableList<T>

    fun insert(entity: T?)

    fun update(entity: T?)

    fun delete(entity: T?)
}