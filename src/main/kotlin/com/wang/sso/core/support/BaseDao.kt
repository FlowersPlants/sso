package com.wang.sso.core.support

import com.baomidou.mybatisplus.core.mapper.BaseMapper

/**
 * 数据库操作基类
 * 2018-11-07 添加mybatis plus插件支持，取消自定义方法
 *
 * @author FlowersPlants
 * @since v1
 */
interface BaseDao<T> : BaseMapper<T>