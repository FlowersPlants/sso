package com.wang.sso.core.support

import com.wang.sso.modules.sys.entity.User
import java.io.Serializable
import java.util.*

/**
 * 数据库实体基类
 */
abstract class BaseModel<T> : Serializable {
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

    var id: String? = null                    //ID

    var name: String? = null                  //名称

    var currentUser: User? = null // UserUtils.getSecurityUser()  //当前用户

    var createBy: User? = null                //创建者
    var createdAt: Date? = null               //创建时间

    var updateBy: User? = null                //更新者
    var updatedAt: Date? = null               //更新时间

    //状态，推荐状态（0-正常；1-删除；2-停用；3-冻结）
    var status: String? = null
    var remarks: String? = null               //备注

    constructor() {
        this.status = BaseModel.NORMAL
    }

    constructor(id: String) {
        this.id = id
        this.status = BaseModel.NORMAL
    }

//    /**
//     * 新增之前执行的方法
//     *
//     * @return 实体
//     */
//    abstract fun preInsert(): T?
//
//    /**
//     * 更新（修改和逻辑删除）之前执行的方法
//     *
//     * @return 实体
//     */
//    abstract fun preUpdate(): T?
}