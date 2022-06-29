package com.kelly.practice.lc.link;

/**
 * author: zongkaili
 * data: 2022/5/3
 * desc: 876. 链表的中间结点
 * 给定一个头结点为 head 的非空单链表，返回链表的中间结点
 * 如果有两个中间结点，则返回第二个中间结点
 * <p>
 * 示例1：
 * 输入：[1,2,3,4,5]
 * 输出：此列表中的结点 3 (序列化形式：[3,4,5])
 * 返回的结点值为 3 。 (测评系统对该结点序列化表述是 [3,4,5])。
 * 注意，我们返回了一个 ListNode 类型的对象 ans，这样：
 * ans.val = 3, ans.next.val = 4, ans.next.next.val = 5, 以及 ans.next.next.next = NULL.
 * <p>
 * 示例2：
 * <p>
 * 输入：[1,2,3,4,5,6]
 * 输出：此列表中的结点 4 (序列化形式：[4,5,6])
 * 由于该列表有两个中间结点，值分别为 3 和 4，我们返回第二个结点。
 */
class MiddleOfLinkedList {
    /**
     * 方法1：数组
     * 时间复杂度：O(N)，其中 N 是给定链表中的结点数目。
     * 空间复杂度：O(N)，即数组用去的空间。
     */
    public ListNode middleNode(ListNode head) {
        ListNode[] tmpArr = new ListNode[100];
        int t = 0;
        while (head != null) {
            tmpArr[t++] = head;
            head = head.next;
        }
        return tmpArr[t / 2];
    }

    /**
     * 方法2：单指针法
     * 两次遍历  第一次遍历时，统计链表中的元素个数 N；
     * 第二次遍历时，我们遍历到第 N/2 个元素（链表的首节点为第 0 个元素）时，将该元素返回即可。
     * <p>
     * 时间复杂度：O(N)，其中 N 是给定链表的结点数目。
     * 空间复杂度：O(1)，只需要常数空间存放变量和指针
     */
    public ListNode middleNode2(ListNode head) {
        int n = 0;
        ListNode cur = head;
        while (cur != null) {
            ++n;
            cur = cur.next;
        }
        int k = 0;
        cur = head;
        while (k < n / 2) {
            ++k;
            cur = cur.next;
        }
        return cur;
    }

    /**
     * 方法3：快慢指针法
     * 时间复杂度：O(N)，其中 N 是给定链表的结点数目。
     * 空间复杂度：O(1)，只需要常数空间存放 slow 和 fast 两个指针。
     */
    public ListNode middleNode3(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

}
