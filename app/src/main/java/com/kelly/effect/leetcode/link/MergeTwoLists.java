package com.kelly.effect.leetcode.link;


/**
 * author: zongkaili
 * data: 2020/3/22
 * desc: 21. 合并两个有序链表
 * 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 
 * <p>
 * 示例：
 * 输入：1->2->4, 2->3->4
 * 输出：1->2->2->3->4->4
 */
public class MergeTwoLists {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        public ListNode setNext(ListNode next) {
            this.next = next;
            return this;
        }
    }

    private static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        //solution1: 递归
//        if (l1 == null) return l2;
//        if (l2 == null) return l1;
//        if (l1.val < l2.val) {
//            l1.next = mergeTwoLists(l1.next, l2);
//            return l1;
//        }
//        l2.next = mergeTwoLists(l1, l2.next);
//        return l2;

        //solution2: 迭代
        ListNode preHead = new ListNode(-1);
        ListNode prev = preHead;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                prev.next = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }
        prev.next = l1 == null ? l2 : l1;
        return preHead.next;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1).setNext(new ListNode(2).setNext(new ListNode(3)));
        ListNode l2 = new ListNode(2).setNext(new ListNode(3).setNext(new ListNode(4)));
        ListNode listNode = mergeTwoLists(l1, l2);
        System.out.println(listNode.next);
    }

}
