package com.wang.sso.core.support

/**
 * 数据库操作基类
 */
interface BaseDao<T : BaseModel<T>> {

    operator fun get(entity: T): T

    operator fun get(id: String): T


    fun findCount(): Long

    fun findAllList(entity: T): MutableList<T>

    fun findList(entity: T): MutableList<T>


    fun insert(entity: T): Long

    fun batchInsert(list: MutableList<T>): Long

    fun insertSelective(entity: T): Long


    fun update(entity: T): Long

    fun updateStatus(entity: T): Long

    fun batchUpdate(list: MutableList<T>): Long

    fun updateByPrimaryBySelective(entity: T): Long


    //----------- 以下删除为物理删除 ------------
    fun delete(id: String): Long

    fun delete(entity: T): Long

    fun batchDelete(list: MutableList<T>): Long
}