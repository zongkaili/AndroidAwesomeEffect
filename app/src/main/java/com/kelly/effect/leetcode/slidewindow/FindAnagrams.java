package com.kelly.effect.leetcode.slidewindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2014-2021 Zuoyebang, All rights reserved.
 *
 * @author zongkaili | zongkaili@zuoyebang.com
 * @version 1.0.0 | 2021/8/2 | zongkaili 初始版本
 * @date 2021/8/2 12:21 上午
 * @description 438. 找到字符串中所有字母异位词
 * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
 * 异位词 指字母相同，但排列不同的字符串。
 * Tips：换句话说，就是找到 s 中所有 p 的排列，返回它们的起始索引
 * <p>
 * 示例 1:
 * 输入: s = "cbaebabacd", p = "abc"
 * 输出: [0,6]
 * 解释:
 * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
 * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
 * <p>
 * 示例 2:
 * 输入: s = "abab", p = "ab"
 * 输出: [0,1,2]
 * 解释:
 * 起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
 * 起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
 * 起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
 */
public class FindAnagrams {
    /**
     * 找到字符串 s 中字符串 p 的所有字母异位词子串
     * 解法：滑动窗口
     *
     * @return 字符串s中所有字符串p的字母异位词的起始位置
     */
    private static List<Integer> findAnagrams(String s, String p) {
        //计算字符串p中各元素的出现次数
        int[] pPreq = new int[26];
        for (int i = 0; i < p.length(); i++) {
            pPreq[p.charAt(i) - 'a']++;
        }
        int start = 0, end = -1;
        List<Integer> resultList = new ArrayList<>();
        while (start < s.length()) {
            //移动左右指针
            if (end + 1 < s.length() && end - start + 1 < p.length()) {
                //向右移动右指针：窗口的长度比p的长度小
                end++;
            } else {
                //向右移动左指针：窗口的长度等于p的长度时
                start++;
            }
            //判断窗口当前窗口子串是不是字符串p的字母异位词
            if (end - start + 1 == p.length() && isAnagrams(s.substring(start, end + 1), pPreq)) {
                resultList.add(start);
            }
        }
        return resultList;
    }

    /**
     * 判断窗口当前窗口子串是不是字符串p的字母异位词
     * Tips：在长度一致的情况下，比较各字母的出现次数
     *
     * @param window 窗口字符串
     * @param pFreq  字符串p中各字母的出现次数
     */
    private static boolean isAnagrams(String window, int[] pFreq) {
        int[] windowFreq = new int[26];
        for (int i = 0; i < window.length(); i++) {
            windowFreq[window.charAt(i) - 'a']++;
        }
        for (int i = 0; i < pFreq.length; i++) {
            if (windowFreq[i] != pFreq[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("找到字符串中所有字母异位词：" + findAnagrams("cbaebabacd", "adc"));
    }
}
