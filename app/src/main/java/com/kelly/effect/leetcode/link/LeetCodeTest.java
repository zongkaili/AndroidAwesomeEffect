package com.kelly.effect.leetcode.link;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: zongkaili
 * data: 2019-12-11
 * desc:
 */
public class LeetCodeTest {
    public static void main(String[] args) {
//        int num = reverse(123);
//        System.out.println(" num = " + num);
//
//        String s = "XIV";
//        int romanToInt = romanToInt(s);
//        System.out.println("romanToInt == " + romanToInt);

//        String[] strs = new String[]{"flower", "f", "flight"};
//        System.out.println(" longestCommonPrefix == " + longestCommonPrefix(strs));

//        List list = letterCombinations("238");
//        for (Object o : list) {
//            System.out.println(o);
//        }

        ListNode node = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, null)))));
        ListNode node1 = removeNthFromEnd(node, 2);
        System.out.println(node1.val);
    }

    public static int reverse(int x) {
        int num = 0;
        while (x != 0) {
            int p = x % 10;
            x /= 10;
            //处理越界问题 2147483647
            if (num > Integer.MAX_VALUE / 10 || (num == Integer.MAX_VALUE / 10 && p > 7)
                    || num < Integer.MIN_VALUE / 10 || (num == Integer.MIN_VALUE / 10 && p < -8)) {
                return 0;
            }
            num = num * 10 + p;
        }
        return num;
    }

    /**
     * 将str转换为int
     *
     * @param str "   -4193 with words " -----4193
     * @return
     */
    public static int myAtoi(String str) {
        if (str == null || str.trim().length() == 0) {
            return 0;
        }
        //1.遍历查找第一个非空字符，赋值正负号
        int sign = 1;//标记正负，
        int i = 0, ans = 0, len = str.length();
        while (i < len && str.charAt(i) == ' ') ++i;
        if (i < len && (str.charAt(i) == '-' || str.charAt(i) == '+')) {
            sign = str.charAt(i++) == '+' ? 1 : -1;
        }
        //2.遍历剩下的字符，int字符，str.char[i] - '0'的值在0到9之间
        for (; i < len; i++) {
            int temp = str.charAt(i) - '0';
            if (temp < 0 || temp > 9) break;
            //3.处理临界值:  "   -2147483647 with words " -----> -2147483647
            if (ans > Integer.MAX_VALUE / 10 || (ans == Integer.MAX_VALUE / 10 && (sign == 1 && temp > 7
                    || sign == -1 && temp > 8))) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            ans = ans * 10 + temp;
        }

        return sign * ans;
    }

    public static boolean isMatch(String s, String p) {
        if (p.isEmpty()) return s.isEmpty();
        if (p.length() == 1) {
            return s.length() == 1 && (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.');
        }
        if (p.charAt(1) != '*') {
            if (s.isEmpty()) return false;
            return (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.') && isMatch(s.substring(1), p.substring(1));
        }
        while (!s.isEmpty() && (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.')) {
            if (isMatch(s, p.substring(2))) return true;
            s = s.substring(1);
        }
        return isMatch(s, p.substring(2));
    }

    /**
     * 13.罗马数字转整数
     *
     * @param s
     * @return
     */
    public static int romanToInt(String s) {
        if (s == null || s.isEmpty()) return 0;
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        int len = s.length();
        int sum = map.get(s.charAt(len - 1));
        for (int i = len - 2; i >= 0; i--) {
            if (map.get(s.charAt(i)) < sum) {
                sum -= map.get(s.charAt(i));
            } else {
                sum += map.get(s.charAt(i));
            }
        }
        return sum;
    }

    /**
     * 14.最长公共前缀
     *
     * @param strs
     * @return
     */
    public static String longestCommonPrefix(String[] strs) {
        //解法1：
//        if (strs == null || strs.length == 0) return "";
//        char c;
//        for (int i = 0; i < strs[0].length(); i++) {
//             c = strs[0].charAt(i);
//            for (int j = 1; j < strs.length; j++) {
//                if (strs[j].length() == i || strs[j].charAt(i) != c) {
//                    return strs[0].substring(0, i);
//                }
//            }
//        }
//        return strs[0];

        //解法2：
        if (strs == null || strs.length == 0) return "";
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            System.out.println(" start index ----> " + strs[i].indexOf(prefix) + ", i ----- " + i);
            while (strs[i].indexOf(prefix) != 0) {
                System.out.println(" while start index ----> " + strs[i].indexOf(prefix) + ", prefix >>> " + prefix);
                prefix = prefix.substring(0, prefix.length() - 1);
                System.out.println(" while end   index ----> " + strs[i].indexOf(prefix) + ", prefix >>> " + prefix);
                if (prefix.isEmpty()) return "";
            }
        }
        return prefix;

    }

    /**
     * 17.电话号码的字母组合
     *
     * @param digits
     * @return
     */
    private static Map<String, String> map = new HashMap<>();

    public static List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() <= 0) return Collections.emptyList();
        map.put("2", "abc");
        map.put("3", "def");
        map.put("4", "ghi");
        map.put("5", "jkl");
        map.put("6", "mno");
        map.put("7", "pqrs");
        map.put("8", "tuv");
        map.put("9", "wxyz");
        List<String> list = new ArrayList<>();
        helper(list, digits, "");
        return list;
    }

    private static void helper(List<String> list, String digits, String ans) {
        if (digits.length() == 0) {
            list.add(ans);
            return;
        }
        String digit = digits.substring(0, 1);
        String letters = map.get(digit);
        if (letters == null || letters.length() == 0) return;
        for (int i = 0; i < letters.length(); i++) {
            String letter = String.valueOf(letters.charAt(i));
            helper(list, digits.substring(1), ans + letter);
        }
    }

    /**
     * 19. 删除链表的倒数第N个节点
     * 例如：输入1->2->3->4->5->6 和 2   删除4后链表变为：1->2->3->5->6
     * 返回头节点1
     */
    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    private static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode preN = head;
        ListNode afterPreN = head;
        //先让双指针相差n个元素
        while (n-- != 0) {
            afterPreN = afterPreN.next;
        }
        if (afterPreN == null) {
            //如果一开始后面的指针为null，则删除第一个元素
            head = head.next;
        } else {
            while (afterPreN.next != null) {
                //双指针同时向后移动，当后面的指针移动到最后的元素时(即直到afterPreN.next == null)，则前面的指针后面的元素即为要删除的元素
                preN = preN.next;
                afterPreN = afterPreN.next;
            }
            preN.next = preN.next.next;
        }
        return head;
    }

}
