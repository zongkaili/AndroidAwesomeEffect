package com.kelly.effect.leetcode.link;

/**
 * author: zongkaili
 * data: 2022/5/3
 * desc: 链表结点
 */
class ListNode {
    int val;
    ListNode next;
    ListNode() { }
    ListNode(int val) {
        this.val = val;
    }
    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
