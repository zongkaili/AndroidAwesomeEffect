package com.kelly.effect.leetcode.tree;

import java.util.Random;

/**
 * 108. 将有序数组转换为二叉搜索树
 * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵 高度平衡 二叉搜索树。
 * 高度平衡 二叉树是一棵满足「每个节点的左右两个子树的高度差的绝对值不超过 1 」的二叉树。
 * 示例1：
 * 输入：nums = [-10,-3,0,5,9]
 * 输出：[0,-3,9,-10,null,5]
 * 解释：[0,-10,5,null,-3,null,9] 也将被视为正确答案：
 *
 * 示例2：
 * 输入：nums = [1,3]
 * 输出：[3,1]
 * 解释：[1,3] 和 [3,1] 都是高度平衡二叉搜索树。
 *
 * @author zkl
 */
public class TreeFromSortedArray {
    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() { }
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 方法：中序遍历
     * 递归创建二叉树，每个子树的根节点有以下几种选择方式：
     * a.总是选择中间数字或者中间位置左边的数字作为子树根节点，这种可以用整除来计算，(left + right）/ 2;
     * b.总是选择中间位置右边的数字作为子树根节点，这种需要节点数+1再整除，(left + right) / 2;
     * c.随机选择中间左边或者右边数字，对于本题，都能构造高度平衡的二叉搜索树，random.nextInt(2)
     * 选择任意一个中间位置数字作为根节点
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return buildBinaryTree(nums, 0, nums.length - 1);
    }

    Random random = new Random();
    private TreeNode buildBinaryTree(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        // 总是选择中间数字或者中间位置左边的数字作为根节点
//        int middle = (left + right) / 2;
        // 总是选择中间位置右边的数字作为根节点
//        int middle = (left + right + 1) / 2;
        int middle = (left + right + random.nextInt(2)) / 2;
        TreeNode node = new TreeNode(nums[middle]);
        node.left = buildBinaryTree(nums, left, middle - 1);
        node.right = buildBinaryTree(nums, middle + 1, right);
        return node;
    }
}
