package com.wang.sso.modules.sys.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.wang.sso.core.mybatis.annotation.MyBatisDao
import com.wang.sso.modules.sys.entity.Log

@MyBatisDao(value = "logDao", entity = Log::class)
interface ILogDao : BaseMapper<Log>