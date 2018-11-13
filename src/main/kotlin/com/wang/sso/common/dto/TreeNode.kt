package com.wang.sso.common.dto

import java.io.Serializable

/**
 * 树节点，树结构的模型需要继承此类
 * @author FlowersPlants
 * @since v1
 */
open class TreeNode : Serializable {
    companion object {
        const val serialVersionUID = 5640224091610000001L
    }

    var id: String? = null
    var parentId: String? = null
    var children: MutableList<TreeNode>? = null
    var sort: Int? = null

    fun addChild(node: TreeNode) {
        children?.add(node)
    }

    /**
     * 通过sort排序
     * @param comparator 比较器 若为null则默认比较sort
     */
    fun sortChildren(comparator: Comparator<in TreeNode>?) {
        if (children != null) {
            children!!.let { c ->
                c.sortWith(comparator ?: Comparator { o1, o2 -> o1.sort!! - o2.sort!! })
                // 所有子节点排个序
                c.forEach {
                    it.sortChildren(comparator)
                }
            }

        }
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (parentId?.hashCode() ?: 0)
        result = 31 * result + (children?.hashCode() ?: 0)
        result = 31 * result + (sort ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TreeNode) return false

        if (id != other.id) return false
        if (parentId != other.parentId) return false
        if (children != other.children) return false
        if (sort != other.sort) return false

        return true
    }
}