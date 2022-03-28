package com.kelly.effect.leetcode.tree.bst;

import java.util.ArrayList;
import java.util.List;

/**
 * author: zongkaili
 * data: 2022/3/28
 * desc: 1382. 将二叉搜索树变平衡
 * 给你一棵二叉搜索树，请你返回一棵平衡后的二叉搜索树，新生成的树应该与原来的树有着相同的节点值。如果有多种构造方法，请你返回任意一种。
 * 如果一棵二叉搜索树中，每个节点的两棵子树高度差不超过 1 ，我们就称这棵二叉搜索树是平衡的 。
 * <p>
 * 示例1：
 * 输入：root = [1,null,2,null,3,null,4,null,null]
 * 输出：[2,1,3,null,null,null,4]
 * 解释：这不是唯一的正确答案，[3,1,4,null,2,null,null] 也是一个可行的构造方案。
 * <p>
 * 示例2：
 * 输入: root = [2,1,3]
 * 输出: [2,1,3]
 */
class BalanceBST {
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
     * 方法：贪心构造
     */
    List<Integer> inorderSeq = new ArrayList<Integer>();

    public TreeNode balanceBST(TreeNode root) {
        // 通过中序遍历将原来的二叉搜索树转化为一个有序序列
        getInorder(root);
        return build(0, inorderSeq.size() - 1);
    }

    public void getInorder(TreeNode o) {
        if (o.left != null) {
            getInorder(o.left);
        }
        inorderSeq.add(o.val);
        if (o.right != null) {
            getInorder(o.right);
        }
    }

    public TreeNode build(int l, int r) {
        int mid = (l + r) >> 1;
        TreeNode o = new TreeNode(inorderSeq.get(mid));
        if (l <= mid - 1) {
            o.left = build(l, mid - 1);
        }
        if (mid + 1 <= r) {
            o.right = build(mid + 1, r);
        }
        return o;
    }
}
