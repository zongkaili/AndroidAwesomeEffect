package com.kelly.practice.lc.number;

import java.util.Arrays;

/**
 * author: zongkaili
 * data: 2022/7/10
 * desc: 1979. 找出数组的最大公约数
 * 给你一个整数数组 nums ，返回数组中最大数和最小数的 最大公约数 。
 * 两个数的 最大公约数 是能够被两个数整除的最大正整数。
 *
 * 示例 1：
 * 输入：nums = [2,5,6,9,10]
 * 输出：2
 * 解释：
 * nums 中最小的数是 2
 * nums 中最大的数是 10
 * 2 和 10 的最大公约数是 2
 *
 * 示例 2：
 * 输入：nums = [7,5,6,8,3]
 * 输出：1
 * 解释：
 * nums 中最小的数是 3
 * nums 中最大的数是 8
 * 3 和 8 的最大公约数是 1
 *
 * 示例 3：
 * 输入：nums = [3,3]
 * 输出：3
 * 解释：
 * nums 中最小的数是 3
 * nums 中最大的数是 3
 * 3 和 3 的最大公约数是 3
 */
class GreatestCommonDivisorOfArray {
    public static void main(String[] args) {
        int[] nums = new int[]{7,5,6,8,3};
        System.out.println("数组" + Arrays.toString(nums) + "的最大公约数是：" + findGCD(nums));
    }
    public static int findGCD(int[] nums) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int num : nums) {
            if (num > max) {
                max = num;
            }
            if (num < min) {
                min = num;
            }
        }
        return gcd(min, max);
    }

    /**
     * 求最大公约数
     * 辗转相除法
     */
    public static int gcd(int x, int y) {
        return y == 0 ? x : gcd(y, x % y);
    }
}
