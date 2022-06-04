package com.kelly.effect.leetcode.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * author: zongkaili
 * data: 2020/4/6
 * desc: 102. 二叉树的层序遍历
 * 给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
 * <p>
 * 示例：
 * 二叉树：[3,9,20,null,null,15,7],
 *   3
 *  / \
 * 9  20
 *   /  \
 *  15   7
 * 返回其层次遍历结果：
 * [
 * [3],
 * [9,20],
 * [15,7]
 * ]
 */
public class TreeTraversalLevelOrder {

    private static List<List<Integer>> levels = new ArrayList<List<Integer>>();

    /**
     * 方法1：递归， 深度优先搜索
     */
    private static List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) return levels;
        helper(root, 0);
        return levels;
    }

    private static void helper(TreeNode node, int level) {
        if (levels.size() == level) {
            levels.add(new ArrayList<>());
        }

        levels.get(level).add(node.val);

        if (node.left != null) {
            helper(node.left, level + 1);
        }
        if (node.right != null) {
            helper(node.right, level + 1);
        }
    }

    /**
     * 方法2：循环，栈
     */
    private static List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<>();
        if (root == null) return levels;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int level = 0;
        while (!queue.isEmpty()) {
            levels.add(new ArrayList<>());
            int level_length = queue.size();
            for(int i = 0; i < level_length; ++i) {
                TreeNode node = queue.remove();
                levels.get(level).add(node.val);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            level++;
        }
        return levels;
    }

    /**
     * 103. 二叉树的锯齿形层次遍历
     * 给定一个二叉树，返回其节点值的锯齿形层次遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
     *
     * 例如：
     * 给定二叉树 [3,9,20,null,null,15,7],
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回锯齿形层次遍历如下：
     * [
     *   [3],
     *   [20,9],
     *   [15,7]
     * ]
     */
    private static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> results = new ArrayList<>();

        //solution1: BFS（广度优先遍历）
        BFS(root, results);

        //solution2: DFS （深度优先遍历）
        DFS(root, 0, results);

        return results;
    }

    /**
     * 二叉树的锯齿形层次遍历
     * 方法1：BFS（广度优先遍历）
     */
    private static void BFS(TreeNode root, List<List<Integer>> results) {
        // add the root element with a delimiter to kick off the BFS loop
        LinkedList<TreeNode> node_queue = new LinkedList<>();
        node_queue.addLast(root);
        node_queue.addLast(null);

        LinkedList<Integer> level_list = new LinkedList<>();
        boolean is_order_left = true;

        while (node_queue.size() > 0) {
            TreeNode curr_node = node_queue.pollFirst();
            if (curr_node != null) {
                if (is_order_left)
                    level_list.addLast(curr_node.val);
                else
                    level_list.addFirst(curr_node.val);

                if (curr_node.left != null)
                    node_queue.addLast(curr_node.left);
                if (curr_node.right != null)
                    node_queue.addLast(curr_node.right);

            } else {
                // we finish the scan of one level
                results.add(level_list);
                level_list = new LinkedList<Integer>();
                // prepare for the next level
                if (node_queue.size() > 0)
                    node_queue.addLast(null);
                is_order_left = !is_order_left;
            }
        }
    }

    /**
     * 二叉树的锯齿形层次遍历
     * 方法2：DFS （深度优先遍历）
     */
    private static void DFS(TreeNode root, int level, List<List<Integer>> results) {
        if (level >= results.size()) {
            LinkedList<Integer> newLevel = new LinkedList<>();
            newLevel.add(root.val);
            results.add(newLevel);
        } else {
            if (level % 2 == 0)
                results.get(level).add(root.val);
            else
                results.get(level).add(0, root.val);
        }

        if (root.left != null) DFS(root.left, level + 1, results);
        if (root.right != null) DFS(root.right, level + 1, results);
    }

    /**
     * 107. 二叉树的层次遍历 II --- 自底向上
     * 给定一个二叉树，返回其节点值自底向上的层次遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
     *
     * 例如：
     * 给定二叉树 [3,9,20,null,null,15,7],
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回其自底向上的层次遍历为：
     * [
     *   [15,7],
     *   [9,20],
     *   [3]
     * ]
     */
    private static List<List<Integer>> levelOrderBottom(TreeNode root) {
        if (root == null) return Collections.emptyList();
        List<List<Integer>> list = new LinkedList<>();
        //solution1: 宽度优先搜索
        helperWithBreadthSearch(list, root);

        //solution2: 深度优先搜索
        helperWithDepthSearch(list, root, 0);

        return list;
    }

    /**
     * 二叉树的层次遍历 II --- 自底向上
     * 方法1：宽度优先搜索
     */
    private static void helperWithBreadthSearch(List<List<Integer>> list, TreeNode root) {
        LinkedList<TreeNode> q = new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()) {
            int size = q.size();
            List<Integer> sub = new LinkedList<>();
            for(int i = 0; i < size; ++i) {
                TreeNode node = q.removeFirst();
                sub.add(node.val);
                if (node.left != null) {
                    q.add(node.left);
                }
                if (node.right != null) {
                    q.add(node.right);
                }
            }
            list.add(0, sub);
        }
    }

    /**
     * 二叉树的层次遍历 II --- 自底向上
     * 方法2：深度优先搜索
     */
    private static void helperWithDepthSearch(List<List<Integer>> list, TreeNode root, int level) {
        if (root == null) return;
        if (level >= list.size()) {
            list.add(0, new LinkedList<>());
        }

        list.get(list.size() - level - 1).add(root.val);

        if (root.left != null) {
            helperWithDepthSearch(list, root.left, level + 1);
        }
        if (root.right != null) {
            helperWithDepthSearch(list, root.right, level + 1);
        }
    }
}
