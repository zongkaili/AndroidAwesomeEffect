package com.kelly.effect.leetcode.link;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zongkl
 * @date 2020/3/18
 * @desc 141. 环形链表
 * 给定一个链表，判断链表中是否有环。
 * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
 * 示例 1：
 * 输入：head = [3,2,0,-4], pos = 1
 * 输出：true
 * 解释：链表中有一个环，其尾部连接到第二个节点。
 * <p>
 * 142. 环形链表 II
 * 给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
 * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
 * 说明：不允许修改给定的链表。
 * 示例 1：
 * 输入：head = [3,2,0,-4], pos = 1
 * 输出：tail connects to node index 1
 * 解释：链表中有一个环，其尾部连接到第二个节点。
 */
public class LinkListCycle {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * 判断链表是否有环
     * solution1 : 快慢指针
     *
     * @param head
     * @return
     */
    private static boolean isListHasCycleSolution1(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断链表是否有环
     * solution2 : HashSet
     *
     * @param head
     * @return
     */
    private static boolean isListHasCycleSolution2(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (head != null && head.next != null) {
            if (set.contains(head)) {
                return true;
            } else {
                set.add(head);
            }
            head = head.next;
        }
        return false;
    }

    /**
     * 环形链表 II : 找到环入口
     * solution1 : 快慢指针
     *
     * @param head
     * @return
     */
    private static ListNode detectCycleSolution1(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                break;
            }
        }
        if (fast == null || fast.next == null) return null;

        //关键，重置慢指针为头节点，然后快慢指针都以步长1开始循环，直到再次相遇
        slow = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 环形链表 II : 找到环入口
     * solution2 : HashSet
     *
     * @param head
     * @return
     */
    private static ListNode detectCycleSolution2(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        ListNode node = head;
        while (node != null) {
            if (set.contains(node)) {
                return node;
            }
            set.add(node);
            node = node.next;
        }
        return null;
    }

}
