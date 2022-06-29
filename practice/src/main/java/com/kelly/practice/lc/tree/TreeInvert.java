package com.kelly.practice.lc.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * author: zongkaili
 * data: 2020/4/12
 * desc: 226. 翻转二叉树
 * 翻转一棵二叉树。
 * 示例：
 * 输入：
 *      4
 *    /   \
 *   2     7
 *  / \   / \
 * 1   3 6   9
 *
 * 输出：
 *      4
 *    /   \
 *   7     2
 *  / \   / \
 * 9   6 3   1
 */
public class TreeInvert {

    private TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        /*
        solution1: 递归
        时间复杂度：O(n)
        空间复杂度：O(n)
         */
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;

        /*
        solution2: 迭代, 深度优先搜索
        时间复杂度：O(n)
        空间复杂度：O(n)
         */
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            if (current == null) {
                break;
            }
            TreeNode temp = current.left;
            current.left = current.right;
            current.right = temp;
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }

        return root;
    }
}
