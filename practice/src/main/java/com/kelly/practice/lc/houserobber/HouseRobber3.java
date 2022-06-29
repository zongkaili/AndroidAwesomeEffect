package com.kelly.practice.lc.houserobber;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2014-2021 Zuoyebang, All rights reserved.
 *
 * @author zongkaili | zongkaili@zuoyebang.com
 * @version 1.0.0 | 2021/8/6 | zongkaili 初始版本
 * @date 2021/8/6 9:11 下午
 * @description 337. 打家劫舍 III
 * 在上次打劫完一条街道之后和一圈房屋后，小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为“根”。
 * 除了“根”之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。
 * 如果两个直接相连的房子在同一天晚上被打劫，房屋将自动报警。
 * 计算在不触动警报的情况下，小偷一晚能够盗取的最高金额。
 * <p>
 * 示例 1:
 * 输入: [3,2,3,null,3,null,1]
 * 3
 * / \
 * 2   3
 * \   \
 * 3   1
 * 输出: 7
 * 解释:小偷一晚能够盗取的最高金额 = 3 + 3 + 1 = 7.
 * <p>
 * 示例 2:
 * 输入: [3,4,5,1,3,null,1]
 * 3
 * / \
 * 4   5
 * / \   \
 * 1   3   1
 * 输出: 9
 * 解释:小偷一晚能够盗取的最高金额 = 4 + 5 = 9.
 */
public class HouseRobber3 {
    public static class TreeNode {
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
     * f(o) 表示选择 o 节点的情况下，o 节点的子树上被选择的节点的最大权值和
     */
    Map<TreeNode, Integer> f = new HashMap<>();

    /**
     * g(o) 表示不选择 o 节点的情况下，o 节点的子树上被选择的节点的最大权值和；l 和 r 代表 o 的左右孩子。
     */
    Map<TreeNode, Integer> g = new HashMap<>();

    /**
     * 解法：动态规划 状态&选择
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public int rob(TreeNode root) {
        //深度遍历
//        dfs(root);
        //最大收益为根节点被选中和不被选中权值的极大值
//        return Math.max(f.getOrDefault(root, 0), g.getOrDefault(root, 0));

        //解法2：优化空间
        int[] rootStatus = dfs2(root);
        //最大收益为根节点被选中和不被选中权值的极大值
        return Math.max(rootStatus[0], rootStatus[1]);
    }

    /**
     * 深度遍历  递归
     * a.节点被选中，则其左右子节点都不能被选中
     * b.节点没选中，则其左右子节点可选可不选，最大收益取收益极大值
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void dfs(TreeNode node) {
        if (node == null) {
            return;
        }
        dfs(node.left);
        dfs(node.right);
        //节点被选中的权值 = 自己的val + 左子节点没被选中的权值和 + 右子节点没被选中的权值和
        f.put(node, node.val + g.getOrDefault(node.left, 0) + g.getOrDefault(node.right, 0));
        //节点没被选中的权值 = 左子节点被选中和不被选中的权值极大值
        g.put(node, Math.max(f.getOrDefault(node.left, 0), g.getOrDefault(node.left, 0)) + Math.max(f.getOrDefault(node.right, 0), g.getOrDefault(node.right, 0)));
    }

    /**
     * 解法2：优化空间复杂点
     */
    private int[] dfs2(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0};
        }
        int[] l = dfs2(node.left);
        int[] r = dfs2(node.right);
        int selected = node.val + l[1] + r[1];
        int unSelected = Math.max(l[0], l[1]) + Math.max(r[0], r[1]);
        return new int[]{selected, unSelected};
    }


    public static void main(String[] args) {
        System.out.println("打家劫舍的最大收益III：");
    }
}
