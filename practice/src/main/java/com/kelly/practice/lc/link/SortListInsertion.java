package com.kelly.practice.lc.link;

/**
 * author: zongkaili
 * data: 2022/6/5
 * desc: 147. 对链表进行插入排序
 * 给定单个链表的头head，使用 插入排序 对链表进行排序，并返回排序后链表的头。
 * 插入排序算法的步骤:
 *    插入排序是迭代的，每次只移动一个元素，直到所有元素可以形成一个有序的输出列表。
 *    每次迭代中，插入排序只从输入数据中移除一个待排序的元素，找到它在序列中适当的位置，并将其插入。
 *    重复直到所有输入数据插入完为止。
 *   
 * 示例 1：
 * 输入: head = [4,2,1,3]
 * 输出: [1,2,3,4]
 *
 * 示例 2：
 * 输入: head = [-1,5,3,4,0]
 * 输出: [-1,0,3,4,5]
 */
class SortListInsertion {
    /**
     * 方法一：从前往后找插入点
     * 时间复杂度：O(n^2)，其中 n 是链表的长度。
     * 空间复杂度：O(1)。
     */
    public ListNode insertionSortList(ListNode head) {
        if (head == null) {
            return head;
        }
        // 创建哑节点 dummyHead，令 dummyHead.next = head。引入哑节点是为了便于在 head 节点之前插入节点。
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        // lastSorted 为链表的已排序部分的最后一个节点；curr 为待插入的元素
        ListNode lastSorted = head, curr = head.next;
        while (curr != null) {
            // 比较 lastSorted 和 curr 的节点值
            if (lastSorted.val <= curr.val) {
                // 说明 curr 应该位于 lastSorted 之后，将 lastSorted 后移一位，curr 变成新的 lastSorted
                lastSorted = lastSorted.next;
            } else {
                // 否则，从链表的头节点开始往后遍历链表中的节点，寻找插入 curr 的位置。令 prev 为插入 curr 的位置的前一个节点
                ListNode prev = dummyHead;
                while (prev.next.val <= curr.val) {
                    prev = prev.next;
                }
                // 插入 curr
                lastSorted.next = curr.next;
                curr.next = prev.next;
                prev.next = curr;
            }
            // 令 curr = lastSorted.next，此时 curr 为下一个待插入的元素
            curr = lastSorted.next;
        }
        return dummyHead.next;
    }
}
