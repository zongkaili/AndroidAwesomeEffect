package com.kelly.practice.lc.link;

import java.util.HashSet;
import java.util.Set;

/**
 * 160. 相交链表
 * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
 */
public class IntersectionList {

    private class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /**
     * 方法一：哈希集合
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        Set<ListNode> nodes = new HashSet<>();
        ListNode tmpNode = headA;
        while (tmpNode != null) {
            nodes.add(tmpNode);
            tmpNode = tmpNode.next;
        }
        tmpNode = headB;
        while (tmpNode != null) {
            if (nodes.contains(tmpNode)) {
                return tmpNode;
            }
            tmpNode = headB.next;
        }
        return null;
    }

    /**
     * 方法二：双指针
     * 将两个指针依次遍历两个链表的每个节点。具体做法如下：
     * a.每步操作需要同时更新指针 pA 和 pB。
     * b.如果指针 pA 不为空，则将指针 pA 移到下一个节点；如果指针 pB 不为空，则将指针 pB 移到下一个节点。
     * c.如果指针 pA 为空，则将指针 pA 移到链表 headB 的头节点；如果指针 pB 为空，则将指针 pB 移到链表 headA 的头节点。
     * d.当指针 pA 和 pB 指向同一个节点或者都为空时，返回它们指向的节点或者 null。
     */
    public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode pA = headA, pB = headB;
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }
}
