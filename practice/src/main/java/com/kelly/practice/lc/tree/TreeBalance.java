package com.kelly.practice.lc.tree;

/**
 * author: zongkaili
 * data: 2020/4/12
 * desc: 110. 平衡二叉树
 * 给定一个二叉树，判断它是否是高度平衡的二叉树。
 *
 * 本题中，一棵高度平衡二叉树定义为：
 * 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过1。
 *
 * 示例 1:
 * 给定二叉树 [3,9,20,null,null,15,7]
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * 返回 true 。
 *
 * 示例 2:
 * 给定二叉树 [1,2,2,3,3,null,null,4,4]
 *        1
 *       / \
 *      2   2
 *     / \
 *    3   3
 *   / \
 *  4   4
 *
 * 返回 false 。
 */
public class TreeBalance {

    private static boolean treeIsBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        /*
         solution1: 自顶向下的递归，每次调用height时都会计算左右子树的高度，存在冗余
         时间复杂度：O(nlog⁡n)。
         空间复杂度：O(n) 如果树完全倾斜，递归栈可能包含所有节点
         */
        return Math.abs(height(root.left) - height(root.right)) < 2
                && treeIsBalanced(root.left)
                && treeIsBalanced(root.right);
        /*
         solution2: 自底向上的递归，可以优化solution1计算高度的冗余问题
         时间复杂度：O(n)，计算每棵子树的高度和判断平衡操作都在恒定时间内完成。
         空间复杂度：O(n)，如果树不平衡，递归栈可能达到 O(n)。
         */
//        return isBalancedTreeHelper(root).balanced;
    }

    private static int height(TreeNode root) {
        if (root == null) {
            return -1;
        }
        return 1 + Math.max(height(root.left), height(root.right));
    }

    private static final class TreeInfo {
        public final int height;
        public final boolean balanced;

        public TreeInfo(int height, boolean balanced) {
            this.height = height;
            this.balanced = balanced;
        }
    }

    private static TreeInfo isBalancedTreeHelper(TreeNode root) {
        if (root == null) {
            return new TreeInfo(-1, true);
        }
        TreeInfo left = isBalancedTreeHelper(root.left);
        if (!left.balanced) {
            return new TreeInfo(-1, false);
        }
        TreeInfo right = isBalancedTreeHelper(root.right);
        if (!right.balanced) {
            return new TreeInfo(-1, false);
        }
        if (Math.abs(left.height - right.height) < 2) {
            return new TreeInfo(Math.max(left.height, right.height) + 1, true);
        }
        return new TreeInfo(-1, false);
    }

}
