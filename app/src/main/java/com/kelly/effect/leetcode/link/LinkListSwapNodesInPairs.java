package com.kelly.effect.leetcode.link;

/**
 * author: zongkaili
 * data: 2022/6/26
 * desc: 24. 两两交换链表中的节点
 * 给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
 *
 * 示例1：
 * 输入：head = [1,2,3,4]
 * 输出：[2,1,4,3]
 *
 * 示例 2：
 * 输入：head = []
 * 输出：[]
 *
 * 示例 3：
 * 输入：head = [1]
 * 输出：[1]
 */
class LinkListSwapNodesInPairs {
    /**
     * 方法一：递归
     * 时间复杂度：O(n)，其中 n 是链表的节点数量。需要对每个节点进行更新指针的操作。
     * 空间复杂度：O(n)，其中 n 是链表的节点数量。空间复杂度主要取决于递归调用的栈空间。
     */
    public ListNode swapPairs(ListNode head) {
       if (head == null || head.next == null) {
           return head;
       }

       ListNode subHead = swapPairs(head.next.next);

       ListNode temp = head.next;
       temp.next = head;
       head.next = subHead;
       return temp;
    }

    /**
     * 方法二：迭代
     * 时间复杂度：O(n)，其中 n 是链表的节点数量。需要对每个节点进行更新指针的操作。
     * 空间复杂度：O(1)。
     */
    public ListNode swapPairs2(ListNode head) {
        ListNode virtualHead = new ListNode(0);
        virtualHead.next = head;
        ListNode tempNode = virtualHead;
        while (tempNode.next != null && tempNode.next.next != null) {
            ListNode node1 = tempNode.next;
            ListNode node2 = tempNode.next.next;
            tempNode.next = node2;
            node1.next = node2.next;
            node2.next = node1;
            tempNode = node1;
        }
        return virtualHead.next;
    }
}
