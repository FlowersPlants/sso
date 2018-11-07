package com.wang.sso.core.support

import com.baomidou.mybatisplus.annotation.*
import com.fasterxml.jackson.annotation.JsonFormat
import com.wang.sso.modules.sys.entity.User
import java.io.Serializable
import java.util.*

/**
 * 数据库实体基类
 */
abstract class BaseModel() : Serializable {
    companion object {
        private const val serialVersionUID = 1L

        /*
         * 状态，推荐状态（0-正常；1-删除；2-停用；3-冻结）
         */
        const val NORMAL = "0"
        const val DELETE = "1"
        const val DISABLE = "2" // 用户拥有
        const val FREEZE = "3" // 用户拥有
    }

    @TableId(type = IdType.ID_WORKER_STR)
    var id: String? = null                    //ID

    var name: String? = null                  //名称

    @TableField(fill = FieldFill.INSERT)
    var createBy: String? = null              //创建者

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createAt: Date? = null                //创建时间

    @TableField(fill = FieldFill.UPDATE)
    var updateBy: String? = null              //更新者

    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var updateAt: Date? = null                //更新时间

    //状态，推荐状态（0-正常；1-删除；2-停用；3-冻结）
    @TableLogic
    var status: String? = null

    var remarks: String? = null               //备注

    init {
        this.status = BaseModel.NORMAL
    }

    constructor(id: String) : this() {
        this.id = id
    }
}