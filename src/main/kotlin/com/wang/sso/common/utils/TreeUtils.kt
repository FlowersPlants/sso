package com.wang.sso.common.utils

import com.wang.sso.common.dto.TreeNode
import java.util.*

/**
 * 树结构工具类
 * @author FlowersPlants
 * @since v1
 */
@Suppress("UNCHECKED_CAST")
object TreeUtils {

    /**
     * 两层循环建树
     * @param nodes 树节点列表
     * @param rootParentId 根节点id
     */
    fun <T : TreeNode> build(nodes: MutableList<T>, rootParentId: String): MutableList<T> {
        val result = mutableListOf<T>()
        nodes.forEach { node ->
            // 构建根节点,由传入参数决定
            if (rootParentId == node.parentId) {
                result.add(node)
            }

            nodes.forEach {
                if (it.parentId == node.id) {
                    if (node.children == null) {
                        node.children = mutableListOf()
                    }
                    node.addChild(it)
                }
            }
        }
        return result
    }

    /**
     * 递归建树
     * @param nodes 树节点列表
     * @param rootParentId 根节点id
     */
    fun <T : TreeNode> buildByRecursive(nodes: MutableList<T>, rootParentId: String): MutableList<T> {
        val result = mutableListOf<T>()
        nodes.forEach { node ->
            if (rootParentId == node.parentId) {
                result.add(findChildren(node, nodes))
            }
        }
        return result
    }

    /**
     * 递归查找子节点
     *
     * @param nodes 节点列表
     * @param treeNode
     */
    private fun <T : TreeNode> findChildren(treeNode: T, nodes: MutableList<T>): T {
        nodes.forEach {
            if (treeNode.id == it.parentId) {
                if (treeNode.children == null) {
                    treeNode.children = mutableListOf()
                }
                treeNode.addChild(findChildren(it, nodes))
            }
        }
        return treeNode
    }

    /**
     * 深度优先搜索
     * 搜索时只提取目录作为返回值
     * 栈结构
     */
    fun <T : TreeNode> findDFS(nodes: MutableList<T>, transform: (T) -> Boolean): MutableList<T> {
        val result: MutableList<T> = mutableListOf()
        nodes.forEach { node ->
            val stack = Stack<T>()
            stack.add(node)
            while (stack.isNotEmpty()) {
                val temp = stack.pop()
                if (transform(temp)) {
                    result.add(temp)
//                    break
                }
                temp.children?.let { it.forEach { t -> stack.add(t as T) } }
            }
        }
        return result
    }

    /**
     * 广度优先搜索
     * 双端队列
     */
    fun <T : TreeNode> findBFS(nodes: MutableList<T>, transform: (T) -> Boolean): MutableList<T> {
        val result: MutableList<T> = mutableListOf()
        nodes.forEach {
            val deque = ArrayDeque<T>()
            deque.add(it)
            while (deque.isNotEmpty()) {
                val temp = deque.pop()
                if (transform(temp)) {
                    result.add(temp)
                }
                temp.children?.let { c -> c.forEach { t -> deque.add(t as T) } }
            }
        }
        return result
    }
}
