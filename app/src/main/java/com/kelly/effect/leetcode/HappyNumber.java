package com.kelly.effect.leetcode;

import java.util.HashSet;

/**
 * author: zongkaili
 * data: 2020/3/15
 * desc: 202.快乐数
 * 编写一个算法来判断一个数是不是“快乐数”。
 *
 * 一个“快乐数”定义为：对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和，然后重复这个过程直到这个数变为 1，也可能是无限循环但始终变不到 1。如果可以变为 1，那么这个数就是快乐数。
 *
 * 示例:
 *
 * 输入: 19
 * 输出: true
 * 解释:
 * 12 + 92 = 82
 * 82 + 22 = 68
 * 62 + 82 = 100
 * 12 + 02 + 02 = 1
 */
public class HappyNumber {
    public static void main(String[] args) {
        int n = 11;
        System.out.println("input ：" + n + ", isHappy : " + isHappy(n));
    }
    private static boolean isHappy(int n) {
//        HashSet<Integer> hashSet = new HashSet<>();
//        int sum = 0;
//        int temp;
//        while (true) {
//            while (n > 0) {
//                temp = n % 10;
//                sum += temp * temp;
//                n /= 10;
//            }
//            if (sum == 1) {
//                return true;
//            }
//            //避免重复，陷入死循环
//            if (hashSet.contains(sum)) {
//                return false;
//            } else {
//                hashSet.add(sum);
//            }
//            n = sum;
//            sum = 0;
//        }

        /**
         * 规律：非快乐数有个特点，循环的数字中必定会出现4，
         * eg: 11
         * 1^2 + 1^2 = 2
         * 2^2 = 4
         * 4^2 = 16
         * 1^2 + 6^2 = 37
         * 3^2 + 7^2 = 58
         * 5^2 + 8^2 = 89
         * 8^2 + 9^2 = 145
         * 1^2 + 4^2 + 5^2 = 42
         * 4^2 + 2^2 = 20
         * 2^2 + 0^2 = 4 //再次出现4陷入死循环
         *
         * 所以有如下优化
         */
        //solution2 :
        int sum = 0;
        int temp;
        while (true) {
            while (n > 0) {
                temp = n % 10;
                sum += temp * temp;
                n /= 10;
            }
            if (sum == 1) {
                return true;
            } else if (sum == 4) {
                return false;
            }
            n = sum;
            sum = 0;
        }
    }
}
