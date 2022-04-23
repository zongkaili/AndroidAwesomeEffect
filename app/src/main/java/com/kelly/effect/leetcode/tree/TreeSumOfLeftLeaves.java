package com.kelly.effect.leetcode.tree;

import com.kelly.effect.leetcode.tree.base.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * author: zongkaili
 * data: 2022/4/23
 * desc: 404. 左叶子之和
 * 给定二叉树的根节点 root ，返回所有左叶子之和。
 * 示例 1：
 *    3
 *   / \
 *  9  20
 *     / \
 *   15   7
 * 输入: root = [3,9,20,null,null,15,7]
 * 输出: 24
 * 解释: 在这个二叉树中，有两个左叶子，分别是 9 和 15，所以返回 24
 *
 * 示例 2:
 * 输入: root = [1]
 * 输出: 0
 */
class TreeSumOfLeftLeaves {
    public int sumOfLeftLeaves(TreeNode root) {
        return sumOfLeftLeaves(root, false);
    }

    /**
     * 方法1：递归
     */
    private int sumOfLeftLeaves(TreeNode node, boolean isLeftNode) {
        if (node == null) return 0;
        if (isLeftNode && node.left == null && node.right == null) {
            return node.val;
        }
        int leftSum = sumOfLeftLeaves(node.left, true);
        int rightSum = sumOfLeftLeaves(node.right, false);
        return leftSum + rightSum;
    }

    /**
     * 方法2：深度优先搜索
     */
    public int sumOfLeftLeaves2(TreeNode root) {
        return root != null ? dfs(root) : 0;
    }

    private int dfs(TreeNode node) {
        int ans = 0;
        if (node.left != null) {
            ans += isLeafNode(node.left) ? node.left.val : dfs(node.left);
        }
        if (node.right != null && !isLeafNode(node.right)) {
            ans += dfs(node.right);
        }
        return ans;
    }

    private boolean isLeafNode(TreeNode node) {
        return node.left == null && node.right == null;
    }

    /**
     * 方法3：广度优先搜索
     */
    public int sumOfLeftLeaves3(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int ans = 0;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.left != null) {
                if (isLeafNode(node.left)) {
                    ans += node.left.val;
                } else {
                    queue.offer(node.left);
                }
            }
            if (node.right != null) {
                if (!isLeafNode(node.right)) {
                    queue.offer(node.right);
                }
            }
        }
        return ans;
    }
}
