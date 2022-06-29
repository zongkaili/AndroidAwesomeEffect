package com.kelly.practice.lc;

/**
 * author: zongkaili
 * data: 2022/3/1
 * desc: 5. 最长回文子串
 * 示例 1：
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 *
 * 示例 2：
 * 输入：s = "cbbd"
 * 输出："bb"
 */
class PalindromeMaxSubStr {
    int start, end;
    public String longestPalindrome(String s) {
        if (s == null) return s;
        int len = s.length();
        if (len <= 1) return s;
        char[] chars = s.toCharArray();
        for (int i = 0; i < len; i++) {
            //处理类似“babad”这样奇数长度回文子串("bad")的情况
            handle(chars, i, i);
            //处理类似“abccbde”这样偶数长度回文子串("bccb")的情况
            handle(chars, i, i + 1);
        }
        return s.substring(start, end + 1);
    }

    private void handle(char[] chars, int l, int r) {
        while(l >= 0 && r < chars.length && chars[l] == chars[r]) {
            --l;
            ++r;
        }
        if (end - start < r - l - 2) {
            start = l + 1;
            end = r - 1;
        }
    }
}
