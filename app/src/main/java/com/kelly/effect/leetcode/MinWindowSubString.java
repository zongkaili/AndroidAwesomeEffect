package com.kelly.effect.leetcode;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2014-2021 Zuoyebang, All rights reserved.
 *
 * @author zongkaili | zongkaili@zuoyebang.com
 * @version 1.0.0 | 2021/7/29 | zongkaili 初始版本
 * @date 2021/7/29 10:41 AM
 * @description 76. 最小覆盖子串
 * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
 * <p>
 * 注意：
 * <p>
 * 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
 * 如果 s 中存在这样的子串，我们保证它是唯一的答案。
 * <p>
 * 示例 1：
 * 输入：s = "ADOBECODEBANC", t = "ABC"
 * 输出："BANC"
 * <p>
 * 示例 2：
 * 输入：s = "a", t = "a"
 * 输出："a"
 * <p>
 * 示例 3:
 * 输入: s = "a", t = "aa"
 * 输出: ""
 * 解释: t 中两个字符 'a' 均应包含在 s 的子串中，
 * 因此没有符合条件的子字符串，返回空字符串。
 */
public class MinWindowSubString {
    /**
     * 记录目标子串的字符及在窗口中出现的次数
     */
    static Map<Character, Integer> orig = new HashMap<>();
    /**
     * 记录滑动窗口中包含的t中character及出现次数
     */
    static Map<Character, Integer> window = new HashMap<>();
    /**
     * 全局的左指针
     */
    static int ansL = -1;
    /**
     * 全局的右指针
     */
    static int ansR = -1;
    /**
     * 记录每次匹配符合时子串的长度
     */
    static int len = Integer.MAX_VALUE;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String minWindow(String s, String t) {
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            orig.put(c, orig.getOrDefault(c, 0) + 1);
        }
        //左右指针
        int left = 0;
        int right = -1;
        while (right < s.length()) {
            right++;
            //记录滑动窗口window中包含的t中字符的出现次数
            if (right < s.length() && orig.containsKey(s.charAt(right))) {
                window.put(s.charAt(right), window.getOrDefault(s.charAt(right), 0) + 1);
            }
            //开始移动左指针，条件是：window中包含了字符串t了
            while (check() && left <= right) {
                if (right - left + 1 < len) {
                    ansL = left;
                    ansR = right;
                    len = right - left + 1;
                }
                if (orig.containsKey(s.charAt(left))) {
                    window.put(s.charAt(left), window.getOrDefault(s.charAt(left), 0) - 1);
                }
                left++;
            }
        }
        //ansR == -1 说明没有符合的，就返回空字符串
        return ansR == -1 ? "" : s.substring(ansL, ansR + 1);
    }

    /**
     * 检查滑动窗口中是否包含了原始字符串t
     * @return true-全包含，false-没有全部包含
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static boolean check() {
        for (Map.Entry<Character, Integer> entry : orig.entrySet()) {
            int value = entry.getValue();
            if (window.getOrDefault(entry.getKey(), 0) < value) {
                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args) {
        String minWindow = minWindow("ADOBECODEBANC", "ABC");
        System.out.println(" 最小覆盖子串：minWindow = " + minWindow);
    }
}
