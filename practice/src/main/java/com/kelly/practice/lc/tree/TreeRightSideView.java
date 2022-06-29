package com.kelly.practice.lc.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * author: zongkaili
 * data: 2022/5/8
 * desc: 199. 二叉树的右视图
 * 给定一个二叉树的 根节点 root，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
 *
 * 示例 1:
 *     1
 *    / \
 *   2   3
 *   \    \
 *    5    4
 * 输入: [1,2,3,null,5,null,4]
 * 输出: [1,3,4]
 */
class TreeRightSideView {
    /**
     * 方法一：深度优先搜索
     * 时间复杂度 : O(n)。深度优先搜索最多访问每个结点一次，因此是线性复杂度。
     * 空间复杂度 : O(n)。最坏情况下，栈内会包含接近树高度的结点数量，占用 O(n) 的空间。
     */
    public List<Integer> rightSideView(TreeNode root) {
        Map<Integer, Integer> rightmostValueAtDepth = new HashMap<Integer, Integer>();
        int max_depth = -1;

        Deque<TreeNode> nodeStack = new ArrayDeque<TreeNode>();
        Deque<Integer> depthStack = new ArrayDeque<Integer>();
        nodeStack.push(root);
        depthStack.push(0);

        while (!nodeStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            int depth = depthStack.pop();

            if (node != null) {
                // 维护二叉树的最大深度
                max_depth = Math.max(max_depth, depth);

                // 如果不存在对应深度的节点我们才插入
                if (!rightmostValueAtDepth.containsKey(depth)) {
                    rightmostValueAtDepth.put(depth, node.val);
                }

                nodeStack.push(node.left);
                nodeStack.push(node.right);
                depthStack.push(depth + 1);
                depthStack.push(depth + 1);
            }
        }

        List<Integer> rightView = new ArrayList<Integer>();
        for (int depth = 0; depth <= max_depth; depth++) {
            rightView.add(rightmostValueAtDepth.get(depth));
        }

        return rightView;
    }

    /**
     * 方法二：广度优先搜索 层序遍历
     */
    public List<Integer> rightSideView2(TreeNode root) {
        if (root == null) return new ArrayList<>();
        Map<Integer, Integer> rightmostValueAtDepth = new HashMap<>();
        int max_depth = -1;

        Queue<TreeNode> nodeQueue = new ArrayDeque<>();
        Queue<Integer> depthQueue = new ArrayDeque<>();
        nodeQueue.add(root);
        depthQueue.add(0);

        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.remove();
            int depth = depthQueue.remove();

            if (node != null) {
                // 维护二叉树的最大深度
                max_depth = Math.max(max_depth, depth);

                // 由于每一层最后一个访问到的节点才是我们要的答案，因此不断更新对应深度的信息即可
                rightmostValueAtDepth.put(depth, node.val);

                if (node.left != null) {
                    nodeQueue.add(node.left);
                    depthQueue.add(depth + 1);
                }
                if (node.right != null) {
                    nodeQueue.add(node.right);
                    depthQueue.add(depth + 1);
                }
            }
        }

        List<Integer> rightView = new ArrayList<Integer>();
        for (int depth = 0; depth <= max_depth; depth++) {
            rightView.add(rightmostValueAtDepth.get(depth));
        }

        return rightView;
    }
}
