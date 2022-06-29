package com.kelly.practice.lc.tree;

/**
 * author: zongkaili
 * data: 2022/5/3
 * desc: 124. 二叉树中的最大路径和
 * 路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。
 * 同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。
 *
 * 路径和 是路径中各节点值的总和。
 *
 * 给你一个二叉树的根节点 root ，返回其 最大路径和 。
 *
 * 示例 1：
 *     1
 *    / \
 *   2   3
 * 输入：root = [1,2,3]
 * 输出：6
 * 解释：最优路径是 2 -> 1 -> 3 ，路径和为 2 + 1 + 3 = 6
 *
 * 示例 2：
 *     -10
 *     /  \
 *    9   20
 *       /  \
 *     15    7
 * 输入：root = [-10,9,20,null,null,15,7]
 * 输出：42
 * 解释：最优路径是 15 -> 20 -> 7 ，路径和为 15 + 20 + 7 = 42
 */
class TreeMaxPathSum {
    int maxSum = Integer.MIN_VALUE;

    /**
     * 方法：递归
     * 时间复杂度：O(N)，其中 N 是二叉树中的节点个数。对每个节点访问不超过 2 次。
     * 空间复杂度：O(N)，其中 N 是二叉树中的节点个数。空间复杂度主要取决于递归调用层数，最大层数等于二叉树的高度，最坏情况下，二叉树的高度等于二叉树中的节点个数。
     */
    public int maxPathSum(TreeNode root) {
        calculateMaxSum(root);
        return maxSum;
    }

    public int calculateMaxSum(TreeNode node) {
        if (node == null) {
            return 0;
        }

        // 递归计算左右子节点的最大贡献值
        // 只有在最大贡献值大于 0 时，才会选取对应子节点
        int leftMaxSum = Math.max(calculateMaxSum(node.left), 0);
        int rightMaxSum = Math.max(calculateMaxSum(node.right), 0);

        // 节点的最大路径和取决于该节点的值与该节点的左右子节点的最大贡献值
        int priceNewPath = node.val + leftMaxSum + rightMaxSum;

        // 更新答案
        maxSum = Math.max(maxSum, priceNewPath);

        // 返回节点的最大贡献值
        return node.val + Math.max(leftMaxSum, rightMaxSum);
    }
}
