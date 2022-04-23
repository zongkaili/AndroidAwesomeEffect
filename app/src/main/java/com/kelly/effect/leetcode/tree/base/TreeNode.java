package com.kelly.effect.leetcode.tree.base;

/**
 * author: zongkaili
 * data: 2022/4/23
 * desc: 二叉树节点类
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode() {}
    public TreeNode(int val) {
        this.val = val;
    }
    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
