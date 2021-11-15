package com.kelly.effect.leetcode;

/**
 * 29. 两数相除
 * 给定两个整数，被除数dividend和除数divisor。将两数相除，要求不使用乘法、除法和 mod 运算符。
 * 返回被除数dividend除以除数divisor得到的商。
 * 整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2
 *
 * 示例1:
 * 输入: dividend = 10, divisor = 3
 * 输出: 3
 * 解释: 10/3 = truncate(3.33333..) = truncate(3) = 3
 *
 * 示例2:
 * 输入: dividend = 7, divisor = -3
 * 输出: -2
 * 解释: 7/-3 = truncate(-2.33333..) = -2
 *
 * 提示：
 * 被除数和除数均为 32 位有符号整数。
 * 除数不为0。
 * 假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−2^31, 2^31− 1]。本题中，如果除法结果溢出，则返回 2^31− 1。
 */
public class Divide {

    public static void main(String[] args) {
        System.out.println("result = " + divide(10, 3));
    }

    private static int divide(int dividend, int divisor) {
        /*
          可以把被除数和除数正数都取反，只有一个取反了即异号，商就取反即去负数
          这样就只需考虑负数的情况，如果负数取反，遇到-2^31取反就越界了
          1.被除数为int最小值-2^31：
               a.除数为1：商为负数，返回int最小值-2^31
               b.除数为-1：商为正数，返回int最大值2^31
          2.除数为int最小值-2^31：
               a.被除数为-2^31：商为1
               b.被除数为其他，商为0，余数舍弃
          3.被除数为0，商为0
         */
        if (dividend == Integer.MIN_VALUE) {
            if (divisor == 1) {
                return Integer.MIN_VALUE;
            } else if (divisor == -1) {
                return Integer.MAX_VALUE;
            }
        }

        if (divisor == Integer.MIN_VALUE) {
            return dividend == Integer.MIN_VALUE ? 1 : 0;
        }

        if (dividend == 0) {
            return 0;
        }

        /*
         一般情况，使用二分查找
         将所有的正数取相反数，这样就只需要考虑一种情况
         被除数和除数异号，商为负数需要取反
         */
        boolean rev = false;
        if (dividend > 0) {
            dividend = -dividend;
            rev = !rev;
        }
        if (divisor > 0) {
            divisor = -divisor;
            rev = !rev;
        }

        // 其他情况，二分查找
        int left = 1, right = Integer.MAX_VALUE, ans = 0;
        while (left <= right) {
            // 找中位数，不能使用除法
            int mid = left + ((right - left) >> 2);
            boolean check = quickAdd(dividend, divisor, mid);
            if (check) {
                ans = mid;
                if (mid == Integer.MAX_VALUE) {
                    break;
                }
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return rev ? -ans : ans;
    }

    /**
     * 快速乘
     * x 和 y 是负数，z 是正数
     * 需要判断 z * y >= x 是否成立
     */
    private static boolean quickAdd(int dividend, int divisor, int mid) {
        int result = 0, add = divisor;
        while (mid != 0) {
            if ((mid & 1) != 0) {
                // 需要保证 result + add >= dividend
                if (result < dividend - add) {
                    return false;
                }
                result += add;
            }
            if (mid != 1) {
                // 需要保证 add + add >= dividend
                if (add < dividend - add) {
                    return false;
                }
                add += add;
            }
            // 不能使用除法，右移1位相当于除以2
            mid >>= 1;
        }
        return true;
    }
}
