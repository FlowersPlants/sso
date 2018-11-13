package com.wang.sso.core.mybatis

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import com.wang.sso.modules.sys.utils.UserUtils
import org.apache.ibatis.reflection.MetaObject
import org.slf4j.LoggerFactory
import java.util.*

/**
 * mybatis plus公共字段自动填充处理器
 * 多线程下不适用？
 * @author FlowersPlants
 * @since v1
 */
class MyBatisPlusMetaObjectHandler : MetaObjectHandler {
    companion object {
        private val logger = LoggerFactory.getLogger(MyBatisPlusMetaObjectHandler::class.java)
    }

    override fun insertFill(metaObject: MetaObject?) {
        try {
            if (getFieldValByName("createBy", metaObject) == null) {
                val currentUser = UserUtils.getCurrentUser().id
                setFieldValByName("createBy", currentUser, metaObject)
            }
            if (getFieldValByName("createAt", metaObject) == null) {
                setFieldValByName("createAt", Date(), metaObject)
            }
        } catch (e: Exception) {
            logger.warn("insertFill error, cause: ", e)
        }
    }

    override fun updateFill(metaObject: MetaObject?) {
        try {
            val currentUser = UserUtils.getCurrentUser().id
            setFieldValByName("updateBy", currentUser, metaObject)

            setFieldValByName("updateAt", Date(), metaObject)
        } catch (e: Exception) {
            logger.warn("updateFill error, cause: ", e)
        }
    }
}