package com.kelly.effect.leetcode;

/**
 * author: zongkaili
 * data: 2020/3/15
 * desc: 206.反转链表
 */
public class ListReverse {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) {
            this.val = val;
        }
        ListNode setNext(ListNode next) {
            this.next = next;
            return this;
        }
    }

    public static ListNode reverseList(ListNode head) {
        //solution1 : 循环
//        ListNode pre = null;
//        ListNode cur = head;
//        ListNode tmp = null;
//        while (cur != null) {
//            tmp = cur.next;
//            cur.next = pre;
//            pre = cur;
//            cur = tmp;
//        }
//        return pre;

        //solution2 : 递归
        if (head == null || head.next == null) {
            //递归终止条件是当前为空，或者下一个节点为空
            return head;
        }
        //这里的cur就是最后一个节点
        ListNode cur = reverseList(head.next);
        //如果链表是 1->2->3->4->5，那么此时的cur就是5
        //而head是4，head的下一个是5，下下一个是空
        //所以head.next.next 就是5->4
        head.next.next = head;
        //防止链表循环，需要将head.next设置为空
        head.next = null;
        //每层递归函数都返回cur，也就是最后一个节点
        return cur;
    }

    public static void main(String[] args) {
        //1->2->3->4-5
        ListNode head = new ListNode(1)
                .setNext(new ListNode(2)
                        .setNext(new ListNode(3)
                                .setNext(new ListNode(4)
                                        .setNext(new ListNode(5)))));
        reverseList(head);
    }
}

