package com.kelly.practice.lc.link;

/**
 * author: zongkaili
 * data: 2022/5/3
 * desc: 剑指 Offer 22. 链表中倒数第k个节点
 * 输入一个链表，输出该链表中倒数第k个节点。为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个节点。
 * 例如，一个链表有 6 个节点，从头节点开始，它们的值依次是 1、2、3、4、5、6，这个链表的倒数第 3 个节点是值为 4 的节点。
 *
 * 示例：
 * 给定一个链表: 1->2->3->4->5, 和 k = 2.
 * 返回链表 4->5.
 */
class KthFromLinkedListEnd {
    /**
     * 方法1：顺序查找
     * 先遍历链表得到长度 n，然后顺序遍历到链表的第 n-k 个节点返回即可
     *
     * 时间复杂度：O(n)，其中 n 为链表的长度，需要两次遍历
     * 空间复杂度：O(1)
     */
    public ListNode getKthFromEnd(ListNode head, int k) {
        int n = 0;
        ListNode node = null;

        for (node = head; node != null; node = node.next) {
            n++;
        }
        for (node = head; n > k; n--) {
            node = node.next;
        }

        return node;
    }

    /**
     * 方法2：双指针
     * 将第一个指针 fast 指向链表的第 k+1 个节点，第二个指针 slow 指向链表的第一个节点，
     * 此时指针 fast 与 slow 二者之间刚好间隔 k 个节点。此时两个指针同步向后走，当第一个指针 fast 走到链表的尾部空节点时，
     * 则此时 slow 指针也一起走了 n-k 步，刚好指向链表的倒数第 k 个节点。
     *
     * 时间复杂度：O(n)，其中 n 为链表的长度。我们使用快慢指针，只需要一次遍历即可，复杂度为 O(n)。
     * 空间复杂度：O(1)。
     */
    public ListNode getKthFromEnd2(ListNode head, int k) {
        ListNode fast = head;
        ListNode slow = head;

        // fast指针先走k步
        while (fast != null && k > 0) {
            fast = fast.next;
            k--;
        }
        // 两个指针同时走，直到fast指针走到尾节点，此时slow指针走了n-k步，刚好就是倒数第k个节点
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        return slow;
    }

}
