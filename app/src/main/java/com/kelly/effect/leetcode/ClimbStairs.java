package com.kelly.effect.leetcode;

/**
 * author: zongkaili
 * data: 2020/4/6
 * desc:
 * 70.爬楼梯
 * 746.使用最小花费爬楼梯
 */
public class ClimbStairs {
    /**
     * 70. 爬楼梯
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
     * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     * 注意：给定 n 是一个正整数。
     * <p>
     * 示例 1：
     * 输入： 2
     * 输出： 2
     * 解释： 有两种方法可以爬到楼顶。
     * 1.  1 阶 + 1 阶
     * 2.  2 阶
     * <p>
     * 示例 2：
     * 输入： 3
     * 输出： 3
     * 解释： 有三种方法可以爬到楼顶。
     * 1.  1 阶 + 1 阶 + 1 阶
     * 2.  1 阶 + 2 阶
     * 3.  2 阶 + 1 阶
     *
     * @param n
     * @return
     */
    private static int climbStairs(int n) {
        /*
          solution1: 暴力法
         */
//        return climb_Stairs(0, n);

        /*
         solution2: 记忆化递归--solution1的优化版
         */
//        int memo[] = new int[n + 1];
//        return climb_Stairs(0, n, memo);

        /*
         solution3: 斐波拉契数列
         */
//        return climbStairsWithFibonacci(n);

        /*
         solution4: 斐波拉契数列优化版
         */
//        return climbStairsWithFibonacciPlus(n);

        /*
         solution5: Binets方法
         */
//        return climbStairsWithBinets(n);

        /*
         solution6: 动态规划
         时间复杂度：O(n)，单循环到n 。
         空间复杂度：O(n)，d 数组用了 n 的空间。
         */
        return climbStairsWithDynamicProgramming(n);
    }

    /**
     * 爬楼梯：动态规划
     * 时间复杂度：O(n)，单循环到n 。
     * 空间复杂度：O(n)，d 数组用了 n 的空间。
     */
    private static int climbStairsWithDynamicProgramming(int n) {
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

    /**
     * 爬楼梯：暴力法
     * 把所有可能爬的阶数进行组合，也就是 1 和 2 。
     * 而在每一步中我们都会继续调用 climbStairs 这个函数模拟爬 1 阶和 2 阶的情形，并返回两个函数的返回值之和。
     * <p>
     * climbStairs(i,n)=(i + 1, n) + climbStairs(i + 2, n)
     * <p>
     * 其中 i 定义了当前阶数，而 n 定义了目标阶数。
     * <p>
     * 时间复杂度：O(2^n)。
     * 空间复杂度：O(n)。
     */
    private static int climb_Stairs(int i, int n) {
        if (i > n) {
            return 0;
        }
        if (i == n) {
            return 1;
        }
        return climb_Stairs(i + 1, n) + climb_Stairs(i + 2, n);
    }

    /**
     * 爬楼梯：记忆化递归，暴力递归的优化版
     * 把每一步的结果存储在 memo 数组之中，每当函数再次被调用，我们就直接从 memo 数组返回结果。
     * 时间复杂度：O(n)，树形递归的大小可以达到 n。
     * 空间复杂度：O(n)，递归树的深度可以达到 n。
     */
    private static int climb_Stairs(int i, int n, int[] memo) {
        if (i > n) {
            return 0;
        }
        if (i == n) {
            return 1;
        }
        if (memo[i] > 0) {
            return memo[i];
        }
        memo[i] = climb_Stairs(i + 1, n, memo) + climb_Stairs(i + 2, n, memo);
        return memo[i];
    }

    /**
     * 爬楼梯：斐波拉契数列
     * 时间复杂度：O(n)，单循环到 n，需要计算第 n 个斐波那契数。
     * 空间复杂度：O(1)，使用常量级空间。
     */
    private static int climbStairsWithFibonacci(int n) {
        if (n == 1) {
            return 1;
        }
        int first = 1;
        int second = 2;
        for (int i = 3; i <= n; i++) {
            int third = first + second;
            first = second;
            second = third;
        }
        return second;
    }

    /**
     * 爬楼梯：斐波拉契数列----优化版
     * 时间复杂度：O(log(n))，pow 方法将会用去 log(n) 的时间。
     * 空间复杂度：O(1)，使用常量级空间。
     */
    private static int climbStairsWithFibonacciPlus(int n) {
        double sqrt5 = Math.sqrt(5);
        double fibn = Math.pow((1 + sqrt5) / 2, n + 1) - Math.pow((1 - sqrt5) / 2, n + 1);
        return (int) (fibn / sqrt5);
    }

    /**
     * 爬楼梯：Binets 方法
     * 时间复杂度：O(log(n))，遍历 log(n) 位。
     * 空间复杂度：O(1)，使用常量级空间。
     */
    private static int climbStairsWithBinets(int n) {
        int[][] q = {{1, 1}, {1, 0}};
        int[][] res = pow(q, n);
        return res[0][0];
    }

    private static int[][] pow(int[][] a, int n) {
        int[][] ret = {{1, 0}, {0, 1}};
        while (n > 0) {
            if ((n & 1) == 1) {
                ret = multiply(ret, a);
            }
            n >>= 1;
            a = multiply(a, a);
        }
        return ret;
    }

    private static int[][] multiply(int[][] a, int[][] b) {
        int[][] c = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                c[i][j] = a[i][0] * b[0][j] + a[i][1] * b[1][j];
            }
        }
        return c;
    }

    /**
     * 746.使用最小花费爬楼梯
     * 数组的每个索引做为一个阶梯，第i 个阶梯对应着一个非负数的体力花费值cost[i](索引从0开始)。
     * 每当你爬上一个阶梯你都要花费对应的体力花费值，然后你可以选择继续爬一个阶梯或者爬两个阶梯。
     * 您需要找到达到楼层顶部的最低花费。在开始时，你可以选择从索引为 0 或 1 的元素作为初始阶梯。
     * <p>
     * 示例1:
     * 输入: cost = [10, 15, 20]
     * 输出: 15
     * 解释: 最低花费是从cost[1]开始，然后走两步即可到阶梯顶，一共花费15。
     * <p>
     * 示例 2:
     * 输入: cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
     * 输出: 6
     * 解释: 最低花费方式是从cost[0]开始，逐个经过那些1，跳过cost[3]，一共花费6。
     * <p>
     * 注意：
     * cost的长度将会在[2, 1000]。
     * 每一个cost[i] 将会是一个Integer类型，范围为[0, 999]。
     * <p>
     * 时间复杂度：O(N)。N 指的是 cost 的长度
     * 空间复杂度：O(1)，只使用了 f1, f2。
     */
    private static int minCostClimbingStairs(int[] cost) {
        /*
        动态规划
        计算花费 f[i] 有一个清楚的递归关系：f[i] = cost[i] + min(f[i+1], f[i+2])。我们可以使用动态规划来实现。
        算法：
        当我们要计算 f[i] 时，要先计算出 f[i+1] 和 f[i+2]。所以我们应该从后往前计算 f。
        在第 i 步，让 f1，f2 为 f[i+1]，f[i+2] 的旧值，并将其更新为f[i]，f[i+1] 的新值。当我们从后遍历 i 时，我们会保持这些更新。在最后答案是 min(f1, f2)。
         */
        int f1 = 0, f2 = 0;
        for (int i = cost.length - 1; i >= 0; --i) {
            int f0 = cost[i] + Math.min(f1, f2);
            f2 = f1;
            f1 = f0;
        }
        return Math.min(f1, f2);
    }


    public static void main(String[] args) {
        int stairs = 10;
        System.out.println(" 爬楼梯，阶数：" + stairs + "， 有几种方法可以爬到楼顶：" + climbStairs(stairs));
    }
}
