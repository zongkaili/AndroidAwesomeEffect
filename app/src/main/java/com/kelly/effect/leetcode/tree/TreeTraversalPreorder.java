package com.kelly.effect.leetcode.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * author: zongkaili
 * data: 2020/4/11
 * desc: 144. 二叉树的前序遍历
 * 给定一个二叉树，返回它的 前序 遍历。
 * <p>
 * 示例:
 * 输入: [1,null,2,3]
 * 1
 * \
 * 2
 * /
 * 3
 * <p>
 * 输出: [1,2,3]
 */
public class TreeTraversalPreorder {
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    private static List<Integer> preorderTraversal(TreeNode node) {
        List<Integer> list = new ArrayList<>();
        //solution1: 基于栈的遍历
        helperWithStack(node, list);

        //solution2: 迭代，宽度优先搜索
        helperWithIteration(node, list);
        return list;
    }

    /**
     * 宽度优先搜索，先压右孩子再压左孩子
     *
     * @param node
     * @param list
     */
    private static void helperWithIteration(TreeNode node, List<Integer> list) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.add(node);
        while (!stack.isEmpty()) {
            TreeNode lastNode = stack.pollLast();
            if (lastNode == null) {
                break;
            }
            list.add(lastNode.val);
            if (lastNode.right != null) {
                stack.add(lastNode.right);
            }
            if (lastNode.left != null) {
                stack.add(lastNode.left);
            }
        }
    }

    private static void helperWithStack(TreeNode node, List<Integer> list) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = node;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                list.add(curr.val);
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            curr = curr.right;
        }
    }
}