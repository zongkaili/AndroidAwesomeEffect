package com.kelly.effect.leetcode.slidewindow;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (c) 2014-2021 Zuoyebang, All rights reserved.
 *
 * @author zongkaili | zongkaili@zuoyebang.com
 * @version 1.0.0 | 2021/8/3 | zongkaili 初始版本
 * @date 2021/8/3 9:29 下午
 * @description 3. 无重复字符的最长子串
 * 给定一个字符串 s ，请你找出其中不含有重复字符的最长子串的长度。
 *
 * 示例 1:
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 *
 * 示例 2:
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 *
 * 示例 3:
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是"wke"，所以其长度为 3。
 *     请注意，你的答案必须是 子串 的长度，"pwke"是一个子序列，不是子串。
 *
 * 示例 4:
 * 输入: s = ""
 * 输出: 0
 */
public class LengthOfLongestSubstring {

    /**
     * 无重复字符的最长子串
     * 解法：滑动窗口
     */
    public static int lengthOfLongestSubstring(String s) {
        //窗口，记录无重复子串
        Set<Character> window = new HashSet<>();
        //右指针
        int right = -1;
        //记录无重复子串的长度
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i != 0) {
                //左指针向右移动一格，即窗口移除最左边的一个字符
                window.remove(s.charAt(i-1));
            }
            while (right + 1 < s.length() && !window.contains(s.charAt(right + 1))) {
                //没有重复的字符，就向右移动右指针，往窗口里加字符
                window.add(s.charAt(right + 1));
                right++;
            }
            length = Math.max(length, right - i + 1);
        }
        return length;
    }

    public static void main(String[] args) {
        String s = "abcabdabdg";
        System.out.println("无重复字符的最长子串：" + lengthOfLongestSubstring(s));
    }
}
