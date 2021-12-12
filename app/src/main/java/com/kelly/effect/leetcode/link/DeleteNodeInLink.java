package com.kelly.effect.leetcode.link;

/**
 * 237. 删除链表中的节点
 * 请编写一个函数，用于 删除单链表中某个特定节点 。
 * 在设计函数时需要注意，你无法访问链表的头节点head ，只能直接访问 要被删除的节点 。
 * 题目数据保证需要删除的节点 不是末尾节点 。
 */
public class DeleteNodeInLink {

    public class ListNode {
       int val;
       ListNode next;
       ListNode(int x) { val = x; }
    }

    /**
     * 不能访问到head,那就只能将下一个节点和当前节点交换，就可以实现删除了
     */
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
