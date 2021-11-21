package com.kelly.effect.leetcode.link;

import java.util.ArrayList;
import java.util.List;

/**
 * 234. 回文链表
 * 给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
 *
 * 示例 1：
 * 输入：head = [1,2,2,1]
 * 输出：true
 *
 * 示例 2：
 * 输入：head = [1,2]
 * 输出：false
 */
public class PalindromeList {

    private class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * 方法一：双指针
     * 1.复制链表值到数组列表中
     * 2.使用双指针法判断是否为回文
     */
    public boolean isPalindrome(ListNode head) {
        List<Integer> list = new ArrayList<>();
        // 1.复制链表值到数组列表中
        ListNode curNode = head;
        while (curNode != null) {
            list.add(curNode.val);
            curNode = curNode.next;
        }
        // 2.使用双指针法判断是否为回文
        int front = 0, back = list.size() - 1;
        while (front < back) {
            if (!list.get(front).equals(list.get(back))) {
                return false;
            }
            front++;
            back--;
        }
        return true;
    }

    /**
     * 方法二：递归
     */
    ListNode frontPointer;
    public boolean isPalindrome2(ListNode head) {
        frontPointer = head;
        return checkRecursively(head);
    }
    private boolean checkRecursively(ListNode curNode) {
        if (curNode != null) {
            if (!checkRecursively(curNode.next)) {
                return false;
            }
            if (frontPointer.val != curNode.val) {
                return false;
            }
            frontPointer = frontPointer.next;
        }
        return true;
    }

    /**
     * 方法三：快慢指针
     * 步骤如下：
     * 1.找到前半部分链表的尾节点
     * 2.反转后半部分链表
     * 3.判断是否回文
     * 4.恢复链表
     * 5.返回结果
     */
    public boolean isPalindrome3(ListNode head) {
        if (head == null) {
            return true;
        }
        // 1.找到前半部分链表的尾节点，如果有奇数个节点，中间节点算在前半部分，因为最终比较时会以遍历到后半部分的尾结点作为结束
        ListNode firstHalfEndNode = endOfFirstHalf(head);
        // 2.反转后半部分链表
        ListNode secondHalfStartNode = reverseList(firstHalfEndNode.next);
        // 3.判断是否回文
        ListNode p1 = head;
        ListNode p2 = secondHalfStartNode;
        boolean result = true;
        while (result && p2 != null) {
            if (p1.val != p2.val) {
               result = false;
            }
            p1 = p1.next;
            p2 = p2.next;
        }
        // 4.恢复链表
        firstHalfEndNode.next = reverseList(secondHalfStartNode);
        return result;
    }

    /**
     * 用"快慢指针"的方式来找链表前半部分的尾结点
     * 快指针每次走两步，慢指针每次走一步
     * 有奇数个结点，中间结点算在前半部分
     * 注：
     *  a.共有偶数个结点，快指针每次走两步，刚好能走到最后一个结点，所以遍历的结束可以用快指针的next为空来判定
     *  b.共有奇数个结点，慢指针每次走两步，只能走到倒数第二个结点，所以遍历的结束应该用快指针的next.next为空来判定
     * @return 前半部分的尾结点
     */
    private ListNode endOfFirstHalf(ListNode head) {
        ListNode fastPointer = head;
        ListNode slowPointer = head;
        while (fastPointer.next == null || fastPointer.next.next == null) {
            fastPointer = fastPointer.next.next;
            slowPointer = slowPointer.next;
        }
        return slowPointer;
    }

    /**
     * 反转链表
     * 这里使用循环的方式，也可以用递归
     * @return 反转后的头结点，也就是反转前的尾结点
     */
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        ListNode tmp;
        while (cur != null) {
            tmp = cur.next;
            cur.next = prev;
            prev = cur;
            cur = tmp;
        }
        return prev;
    }
}
