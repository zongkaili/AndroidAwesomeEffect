package com.kelly.effect.leetcode.tree.bst;

/**
 * author: zongkaili
 * data: 2022/3/28
 * desc: 700. 二叉搜索树中的搜索
 * 给定二叉搜索树（BST）的根节点root和一个整数值val。
 * 你需要在 BST 中找到节点值等于val的节点。 返回以该节点为根的子树。 如果节点不存在，则返回null。
 *
 * 示例1：
 * 输入：root = [4,2,7,1,3], val = 2
 * 输出：[2,1,3]
 *
 * 示例2：
 * 输入：root = [4,2,7,1,3], val = 5
 * 输出：[]
 */
class SearchBST {
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 方法1：递归
     * 二叉搜索树满足如下性质：
     * a.左子树所有节点的元素值均小于根的元素值；
     * b.右子树所有节点的元素值均大于根的元素值。
     *
     * 据此可以得到如下算法：
     * 若 root 为空则返回空节点；
     * 若 val = root.val，则返回 root；
     * 若 val < root.val，递归左子树；
     * 若 val > root.val，递归右子树。
     *
     * 时间复杂度：O(N)，其中 N 是二叉搜索树的节点数。最坏情况下二叉搜索树是一条链，且要找的元素比链末尾的元素值还要小（大），这种情况下我们需要递归 N 次。
     *
     * 空间复杂度：O(N)。最坏情况下递归需要 O(N) 的栈空间。
     */
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        if (val == root.val) {
            return root;
        }
        return searchBST(val < root.val ? root.left : root.right, val);
    }

    /**
     * 方法2：迭代
     */
    public TreeNode searchBST2(TreeNode root, int val) {
        while (root != null) {
            if (val == root.val) {
                return root;
            }
            root = val < root.val ? root.left : root.right;
        }
        return null;
    }

}
