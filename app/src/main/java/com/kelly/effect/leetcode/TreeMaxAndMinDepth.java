package com.kelly.effect.leetcode;

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
        if (node == null) return 0;
        int leftDepth = getTreeMaxDepth(node.left) + 1;
        int rightDepth = getTreeMaxDepth(node.right) + 1;
        return Math.max(leftDepth, rightDepth);
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
    }

}
