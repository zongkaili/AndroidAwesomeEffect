package com.kelly.effect.leetcode.tree;

import android.util.Pair;

import java.util.LinkedList;
import java.util.Queue;

/**
 * author: zongkaili
 * data: 2020/4/16
 * desc: 二叉树的最大/最小深度
 * 104.二叉树的最大深度
 * 111.二叉树的最小深度
 */
public class TreeMaxAndMinDepth {

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 二叉树最大深度。
     * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
     *
     * 示例：
     * 给定二叉树 [3,9,20,null,null,15,7]，
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回它的最大深度 3 。
     * @param node
     * @return
     */
    private static int getTreeMaxDepth(TreeNode node) {
        //solution1: 递归
        if (node == null) return 0;
        int leftDepth = getTreeMaxDepth(node.left) + 1;
        int rightDepth = getTreeMaxDepth(node.right) + 1;
        return Math.max(leftDepth, rightDepth);

        //solution2：宽度优先搜索
//        return getTreeMaxDepthBFS(node);
    }

    private static int getTreeMaxDepthBFS(TreeNode node) {
        Queue<Pair<TreeNode, Integer>> stack = new LinkedList<>();
        if (node != null) {
            stack.add(new Pair(node, 1));
        }

        int depth = 0;
        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> current = stack.poll();
            node = current.first;
            int current_depth = current.second;
            if (node != null) {
                depth = Math.max(depth, current_depth);
                stack.add(new Pair(node.left, current_depth + 1));
                stack.add(new Pair(node.right, current_depth + 1));
            }
        }
        return depth;
    }

    /**
     * 二叉树最小深度。
     * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
     *
     * 示例:
     * 给定二叉树 [3,9,20,null,null,15,7],
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回它的最小深度  2.
     * @param node
     * @return
     */
    private static int getTreeMinDepth(TreeNode node) {
        //solution1: 递归
        if (node == null) return 0;
        if (node.left == null) {
            return getTreeMinDepth(node.right) + 1;
        }
        if (node.right == null) {
            return getTreeMinDepth(node.left) + 1;
        }
        int leftDepth = getTreeMinDepth(node.left);
        int rightDepth = getTreeMinDepth(node.right);
        return Math.min(leftDepth, rightDepth) + 1;

        //solution2: 深度优先搜索
//        return getTreeMinDepthDFS(node);

        //solution3: 宽度优先搜索
//        return getTreeMaxDepthBFS(node);
    }

    private static int getTreeMinDepthDFS(TreeNode node) {
        if (node == null) return 0;
        LinkedList<Pair<TreeNode, Integer>> stack = new LinkedList<>();
        stack.add(new Pair(node, 1));

        int min_depth = Integer.MAX_VALUE;
        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> current = stack.pollLast();
            node = current.first;
            int current_depth = current.second;
            if (node.left == null && node.right == null) {
                min_depth = Math.min(min_depth, current_depth);
            }
            if (node.left != null) {
                stack.add(new Pair(node.left, current_depth + 1));
            }
            if (node.right != null) {
                stack.add(new Pair(node.right, current_depth + 1));
            }
        }
        return min_depth;
    }

    private static int getTreeMinDepthBFS(TreeNode node) {
        if (node == null) return 0;
        LinkedList<Pair<TreeNode, Integer>> stack = new LinkedList<>();
        stack.add(new Pair(node, 1));

        int current_depth = 0;
        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> current = stack.poll();
            node = current.first;
            current_depth = current.second;
            if (node.left == null && node.right == null) {
                break;
            }
            if (node.left != null) {
                stack.add(new Pair(node.left, current_depth + 1));
            }
            if (node.right != null) {
                stack.add(new Pair(node.right, current_depth + 1));
            }
        }
        return current_depth;
    }

}
