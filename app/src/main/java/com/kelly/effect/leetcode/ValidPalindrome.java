package com.kelly.effect.leetcode;

/**
 * author: zongkaili
 * data: 2022/5/4
 * desc: 125. 验证回文串
 * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
 * 说明：本题中，我们将空字符串定义为有效的回文串。
 *
 * 示例 1:
 * 输入: "A man, a plan, a canal: Panama"
 * 输出: true
 * 解释："amanaplanacanalpanama" 是回文串
 *
 * 示例 2:
 * 输入: "race a car"
 * 输出: false
 * 解释："raceacar" 不是回文串
 */
class ValidPalindrome {

    /**
     * 方法一：筛选 + 判断
     * 对字符串 s 进行一次遍历，并将其中的字母和数字字符进行保留，放在另一个字符串 str 中。
     * 这样我们只需要判断 str 是否是一个普通的回文串即可。
     * 判断的方法有两种:
     * 第一种是使用语言中的字符串翻转 API 得到 str 的逆序字符串 str_rev，只要这两个字符串相同，那么 str 就是回文串
     * 第二种是使用双指针，初始时，左右指针分别指向 str 的两侧，随后不断地将这两个指针相向移动，每次移动一步，并判断这两个指针指向的字符是否相同。
     * 当这两个指针相遇时，就说明 str 是回文串。
     *
     * 时间复杂度：O(∣s∣)，其中 ∣s∣ 是字符串 ss 的长度
     * 空间复杂度：O(∣s∣)，由于我们需要将所有的字母和数字字符存放在另一个字符串中，
     * 在最坏情况下，新的字符串 str 与原字符串 s 完全相同，因此需要使用 O(∣s∣) 的空间。
     */
    public boolean isPalindrome(String s) {
        StringBuffer str = new StringBuffer();
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char ch = s.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
                str.append(Character.toLowerCase(ch));
            }
        }
        return verifyPalindrome(str);
    }

    /**
     * 验证字符串是否是回文字符串
     * 方法1：使用语言中的字符串翻转 API 得到 str 的逆序字符串 str_rev，只要这两个字符串相同，那么 str 就是回文串
     */
    private boolean verifyPalindrome(CharSequence str) {
        StringBuffer str_rev = new StringBuffer(str).reverse();
        return str.toString().equals(str_rev.toString());
    }

    /**
     * 验证字符串是否是回文字符串
     * 方法2：使用双指针，初始时，左右指针分别指向 str 的两侧，随后不断地将这两个指针相向移动，
     *       每次移动一步，并判断这两个指针指向的字符是否相同，当这两个指针相遇时，就说明 str 是回文串。
     */
    private boolean verifyPalindrome2(CharSequence str) {
        int n = str.length();
        int left = 0, right = n - 1;
        while (left < right) {
            if (Character.toLowerCase(str.charAt(left)) != Character.toLowerCase(str.charAt(right))) {
                return false;
            }
            ++left;
            --right;
        }
        return true;
    }

    /**
     * 方法二：在原字符串上直接判断
     * 我们可以对方法一中第二种判断回文串的方法进行优化，就可以得到只使用 O(1) 空间的算法。
     * 我们直接在原字符串 s 上使用双指针。在移动任意一个指针时，需要不断地向另一指针的方向移动，
     * 直到遇到一个字母或数字字符，或者两指针重合为止。也就是说，我们每次将指针移到下一个字母字符或数字字符，
     * 再判断这两个指针指向的字符是否相同。
     *
     * 时间复杂度：O(∣s∣)，其中 ∣s∣ 是字符串 s 的长度
     * 空间复杂度：O(1)
     */
    public boolean isPalindrome2(String s) {
        int n = s.length();
        int left = 0, right = n - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                ++left;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                --right;
            }
            if (left < right) {
                if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                    return false;
                }
                ++left;
                --right;
            }
        }
        return true;
    }

}
