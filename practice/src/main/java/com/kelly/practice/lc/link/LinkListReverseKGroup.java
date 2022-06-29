package com.kelly.practice.lc.link;

/**
 * 25. K 个一组翻转链表
 * 给你一个链表，每k个节点一组进行翻转，请你返回翻转后的链表。
 * k是一个正整数，它的值小于或等于链表的长度。
 * 如果节点总数不是k的整数倍，那么请将最后剩余的节点保持原有顺序。
 *
 * 示例1：
 * 输入：head = [1,2,3,4,5], k = 2
 * 输出：[2,1,4,3,5]
 *
 * 示例2：
 * 输入：head = [1,2,3,4,5], k = 3
 * 输出：[3,2,1,4,5]
 *
 * 示例3：
 * 输入：head = [1,2,3,4,5], k = 1
 * 输出：[1,2,3,4,5]
 *
 * 示例4：
 * 输入：head = [1], k = 1
 * 输出：[1]
 */
class LinkListReverseKGroup {

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode virtualHead = new ListNode(0);
        virtualHead.next = head;
        ListNode pre = virtualHead;
        while (head != null) {
            //结束条件
            ListNode tail = pre;
            for (int i = 0; i < k; i++) {
                tail = tail.next;
                if (tail == null) {
                    return virtualHead.next;
                }
            }
            ListNode nextGroupHead = tail.next;
            ListNode[] reversed = reverse(head, tail);
            head = reversed[0];
            tail = reversed[1];
            // 把子链表重新接回原链表
            pre.next = head;
            tail.next = nextGroupHead;
            pre = tail;
            head = tail.next;
        }
        return virtualHead.next;
    }

    /**
     * 反转链表
     * 注：反转后，原来的头结点的next应该是原来tail的next
     * @param head 头结点
     * @param tail 尾结点
     * @return 新的头尾结点数组
     */
    private ListNode[] reverse(ListNode head, ListNode tail) {
        // 双指针
        ListNode prev = tail.next;
        ListNode p = head;
        while (prev != tail) {
            ListNode temp = p.next;
            p.next = prev;
            prev = p;
            p = temp;
        }
        return new ListNode[]{tail, head};
    }
}
