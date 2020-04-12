package com.kelly.effect.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * author: zongkaili
 * data: 2020/4/11
 * desc: 94. 二叉树的中序遍历
 *给定一个二叉树，返回它的中序 遍历。
 *
 * 示例:
 *
 * 输入: [1,null,2,3]
 *    1
 *     \
 *      2
 *     /
 *    3
 *
 * 输出: [1,3,2]
 */
public class TreeTraversalInorder {
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) {
            this.val = val;
        }
    }
    private static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();

        //solution1: 递归
        helperWithRecursion(root, res);

        //solution2: 基于栈的遍历
//        helperWithStack(root, res);

        return res;
    }

    private static void helperWithRecursion(TreeNode node, List<Integer> res) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            helperWithRecursion(node.left, res);
        }
        res.add(node.val);
        if (node.right != null) {
            helperWithRecursion(node.right, res);
        }
    }

    private static void helperWithStack(TreeNode node, List<Integer> res) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = node;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            res.add(curr.val);
            curr = curr.right;
        }
    }

}
