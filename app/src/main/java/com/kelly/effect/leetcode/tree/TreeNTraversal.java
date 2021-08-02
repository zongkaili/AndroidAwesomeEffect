package com.kelly.effect.leetcode.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * author: zongkaili
 * data: 2020/4/12
 * desc: 589. N叉树的前序遍历
 * 给定一个 N 叉树，返回其节点值的前序遍历。
 * 实现方式同二叉树
 */
public class TreeNTraversal {
    private static class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    /**
     * 589. N叉树的前序遍历  实现方式同二叉树
     * 给定一个 N 叉树，返回其节点值的前序遍历。
     *
     * 时间复杂度：时间复杂度：O(M)，其中 M 是 N 叉树中的节点个数。每个节点只会入栈和出栈各一次。
     * 空间复杂度：O(M)，在最坏的情况下，这棵 N 叉树只有 2 层，所有第 2 层的节点都是根节点的孩子。
     * 将根节点推出栈后，需要将这些节点都放入栈，共有 M−1 个节点，因此栈的大小为 O(M)。
     * @param root
     * @return
     */
    private List<Integer> preOrder(Node root) {
        LinkedList<Integer> output = new LinkedList<>();
        if (root == null) return output;
        LinkedList<Node> stack = new LinkedList<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            Node node = stack.pollLast();
            if (node == null) break;
            output.add(node.val);
            Collections.reverse(node.children);
            for (Node item: node.children) {
                stack.add(item);
            }
        }
        return output;
    }

    /**
     * 590. N叉树的后序遍历
     * 时间复杂度：时间复杂度：O(M)，其中 M 是 N 叉树中的节点个数。每个节点只会入栈和出栈各一次。
     * 空间复杂度：O(M)，在最坏的情况下，这棵 N 叉树只有 2 层，所有第 2 层的节点都是根节点的孩子。
     * 将根节点推出栈后，需要将这些节点都放入栈，共有 M−1 个节点，因此栈的大小为 O(M)。
     * @param root
     * @return
     */
    private List<Integer> postOrder(Node root) {
        LinkedList<Integer> output = new LinkedList<>();
        if (root == null) return output;
        LinkedList<Node> stack = new LinkedList<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            Node node = stack.pollLast();
            output.addFirst(node.val);
            for (Node item : node.children) {
                if (item != null) {
                    stack.add(item);
                }
            }
        }
        return output;
    }

    /**
     * 429. N叉树的层序遍历
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
         /*
         solution1: 广度优先搜索
         时间复杂度：O(n)O(n)O(n)。nnn 指的是节点的数量。
         空间复杂度：O(n)O(n)O(n)。
         */
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                level.add(node.val);
                queue.addAll(node.children);
            }
            result.add(level);
        }

         /*
         solution2: 简化版的广度优先搜索
         时间复杂度：O(n)。n指的是节点的数量。
         空间复杂度：O(n)。
         */
        List<Node> previousLayer = Arrays.asList(root);
        while (!previousLayer.isEmpty()) {
            List<Node> currentLayer = new ArrayList<>();
            List<Integer> previousVals = new ArrayList<>();
            for (Node node : previousLayer) {
                previousVals.add(node.val);
                currentLayer.addAll(node.children);
            }
            result.add(previousVals);
            previousLayer = currentLayer;
        }

        /*
         solution3: 递归
         时间复杂度：O(n)。n 指的是节点的数量
         空间复杂度：正常情况 O(log⁡n)，最坏情况 O(n)。运行时在堆栈上的空间
         */
        traverseNode(root, 0);

        return result;
    }

    private List<List<Integer>> result = new ArrayList<>();
    private void traverseNode(Node node, int level) {
        if (result.size() <= level) {
            result.add(new ArrayList<>());
        }
        result.get(level).add(node.val);
        for (Node child : node.children) {
            traverseNode(child, level + 1);
        }
    }

}
