package com.kelly.effect.leetcode;

/**
 * 338. 比特位计数
 * 给你一个整数 n ，对于0 <= i <= n 中的每个 i ，计算其二进制表示中 1 的个数 ，返回一个长度为 n + 1 的数组 ans 作为答案。
 *
 * 示例 1：
 * 输入：n = 2
 * 输出：[0,1,1]
 * 解释：
 * 0 --> 0
 * 1 --> 1
 * 2 --> 10
 *
 * 示例 2：
 * 输入：n = 5
 * 输出：[0,1,1,2,1,2]
 * 解释：
 * 0 --> 0
 * 1 --> 1
 * 2 --> 10
 * 3 --> 11
 * 4 --> 100
 * 5 --> 101
 */
public class CountBits {

    /**
     * 方法1：Brian Kernighan 算法
     * Brian Kernighan算法的原理是：
     * 对于任意整数 x，令 x = x & (x-1)，该运算将 x 的二进制表示的最后一个 1 变成 0。
     * 因此，对 x 重复该操作，直到 x 变成 0，则操作次数即为 x 的「一比特数」。
     *
     * 时间复杂度：O(nlogn)。需要对从 0 到 n 的每个整数使用计算「一比特数」，对于每个整数计算「一比特数」的时间都不会超过 O(logn)。
     * 空间复杂度：O(1)。除了返回的数组以外，空间复杂度为常数。
     */
    public int[] countBits(int n) {
        int[] bits = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            bits[i] = countOnes(i);
        }
        return bits;
    }

    public int countOnes(int x) {
        int ones = 0;
        while (x > 0) {
            x &= (x - 1);
            ones++;
        }
        return ones;
    }

    /**
     * 方法2：动态规划——最高有效位
     * TODO
     * 时间复杂度：O(n)。对于每个整数，只需要 O(1) 的时间计算「一比特数」。
     * 空间复杂度：O(1)。除了返回的数组以外，空间复杂度为常数。
     */
    public int[] countBits2(int n) {
        int[] bits = new int[n + 1];
        int highBit = 0;
        for (int i = 1; i <= n; i++) {
            if ((i & (i - 1)) == 0) {
                highBit = i;
            }
            bits[i] = bits[i - highBit] + 1;
        }
        return bits;
    }

    /**
     * 方法3：动态规划——最低有效位
     * TODO
     * 时间复杂度：O(n)。对于每个整数，只需要 O(1) 的时间计算「一比特数」。
     * 空间复杂度：O(1)。除了返回的数组以外，空间复杂度为常数。
     */
    public int[] countBits3(int n) {
        int[] bits = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            bits[i] = bits[i >> 1] + (i & 1);
        }
        return bits;
    }

    /**
     * 方法4：动态规划——最低设置位
     * TODO
     * 时间复杂度：O(n)。对于每个整数，只需要 O(1) 的时间计算「一比特数」。
     * 空间复杂度：O(1)。除了返回的数组以外，空间复杂度为常数。
     */
    public int[] countBits4(int n) {
        int[] bits = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            bits[i] = bits[i & (i - 1)] + 1;
        }
        return bits;
    }

}
