package com.wang.sso.modules.sys.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.wang.sso.core.mybatis.annotation.MyBatisDao
import com.wang.sso.modules.sys.entity.Dict

@MyBatisDao(value = "dictDao", entity = Dict::class)
interface IDictDao : BaseMapper<Dict>