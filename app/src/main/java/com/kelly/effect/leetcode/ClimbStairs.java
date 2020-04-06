package com.kelly.effect.leetcode;

/**
 * author: zongkaili
 * data: 2020/4/6
 * desc: 70. 爬楼梯
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * 注意：给定 n 是一个正整数。
 *
 * 示例 1：
 * 输入： 2
 * 输出： 2
 * 解释： 有两种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶
 * 2.  2 阶
 *
 * 示例 2：
 * 输入： 3
 * 输出： 3
 * 解释： 有三种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶 + 1 阶
 * 2.  1 阶 + 2 阶
 * 3.  2 阶 + 1 阶
 */
public class ClimbStairs {
    private static int climbStairs(int n) {
        if (n == 1 || n == 2) {
            return n;
        }
        int[] d = new int[n + 1];
        d[1] = 1;
        d[2] = 2;
        for (int i = 3; i <= n; i++) {
            d[i] = d[i - 1] + d[i - 2];
        }
        return d[n];
    }

    public static void main(String[] args) {
        int stairs = 6;
        System.out.println(" 爬楼梯，阶数：" + stairs + "， 有几种方法可以爬到楼顶：" + climbStairs(stairs));
    }
}
