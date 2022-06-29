package com.kelly.practice.lc.link;

/**
 * author: zongkaili
 * data: 2022/6/5
 * desc: 148. 排序链表
 * 给你链表的头结点head，请将其按 升序 排列并返回 排序后的链表。
 *
 * 示例 1：
 * 输入：head = [4,2,1,3]
 * 输出：[1,2,3,4]
 *
 * 示例 2：
 * 输入：head = [-1,5,3,4,0]
 * 输出：[-1,0,3,4,5]
 *
 * 示例 3：
 * 输入：head = []
 * 输出：[]
 */
class SortList {
    /**
     * 方法一：自顶向下归并排序
     * a.找到链表的中点，以中点为分界，将链表拆分成两个子链表。寻找链表的中点可以使用快慢指针的做法，
     *   快指针每次移动 2 步，慢指针每次移动 1 步，当快指针到达链表末尾时，慢指针指向的链表节点即为链表的中点。
     * b.对两个子链表分别排序。
     * c.将两个排序后的子链表合并，得到完整的排序后的链表。可以使用「21. 合并两个有序链表」的做法，将两个有序的子链表进行合并。
     *
     * 时间复杂度：O(nlogn)，其中 n 是链表的长度。
     * 空间复杂度：O(logn)，其中 n 是链表的长度。空间复杂度主要取决于递归调用的栈空间。
     */
    public ListNode sortList(ListNode head) {
       return sortList(head, null);
    }

    public ListNode sortList(ListNode head, ListNode tail) {
        if (head == null) {
            return head;
        }
        if (head.next == tail) {
            head.next = null;
            return head;
        }
        // 找到链表中点
        ListNode slow = head, fast = head;
        while (fast != tail) {
            slow = slow.next;
            fast = fast.next;
            if (fast != tail) {
                fast = fast.next;
            }
        }
        // 对两个子链表排序，再合并两个有序链表 递归
        ListNode mid = slow;
        ListNode list1 = sortList(head, mid);
        ListNode list2 = sortList(mid, tail);
        ListNode sorted = merge(list1, list2);
        return sorted;
    }

    /**
     * 合并两个有序链表
     */
    public ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummyHead = new ListNode(0);
        ListNode temp = dummyHead, temp1 = head1, temp2 = head2;
        while (temp1 != null && temp2 != null) {
            if (temp1.val <= temp2.val) {
                temp.next = temp1;
                temp1 = temp1.next;
            } else {
                temp.next = temp2;
                temp2 = temp2.next;
            }
            temp = temp.next;
        }
        if (temp1 != null) {
            temp.next = temp1;
        } else if (temp2 != null) {
            temp.next = temp2;
        }
        return dummyHead.next;
    }

    /**
     * 方法二：自底向上归并排序 TODO ?
     * 首先求得链表的长度 subLength，然后将链表拆分成子链表进行合并。
     * 具体做法如下:
     * a.用 subLength 表示每次需要排序的子链表的长度，初始时 subLength = 1。
     * b.每次将链表拆分成若干个长度为 subLength 的子链表（最后一个子链表的长度可以小于 subLength），
     *   按照每两个子链表一组进行合并，合并后即可得到若干个长度为 subLength x 2 的有序子链表（最后一个子链表的长度可以小于 subLength × 2）。
     *   合并两个子链表仍然使用「21. 合并两个有序链表」的做法。
     * c.将 subLength 的值加倍，重复第 2 步，对更长的有序子链表进行合并操作，直到有序子链表的长度大于或等于 length，整个链表排序完毕。
     *
     * 时间复杂度：O(nlogn)，其中 n 是链表的长度。
     * 空间复杂度：O(1)。
     */
    public ListNode sortList2(ListNode head) {
        if (head == null) {
            return head;
        }
        int length = 0;
        ListNode node = head;
        while (node != null) {
            length++;
            node = node.next;
        }
        ListNode dummyHead = new ListNode(0, head);
        for (int subLength = 1; subLength < length; subLength <<= 1) {
            ListNode prev = dummyHead, curr = dummyHead.next;
            while (curr != null) {
                ListNode head1 = curr;
                for (int i = 1; i < subLength && curr.next != null; i++) {
                    curr = curr.next;
                }
                ListNode head2 = curr.next;
                curr.next = null;
                curr = head2;
                for (int i = 1; i < subLength && curr != null && curr.next != null; i++) {
                    curr = curr.next;
                }
                ListNode next = null;
                if (curr != null) {
                    next = curr.next;
                    curr.next = null;
                }
                ListNode merged = merge(head1, head2);
                prev.next = merged;
                while (prev.next != null) {
                    prev = prev.next;
                }
                curr = next;
            }
        }
        return dummyHead.next;
    }
}
