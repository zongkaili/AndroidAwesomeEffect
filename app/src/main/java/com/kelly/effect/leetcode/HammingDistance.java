package com.kelly.effect.leetcode;

/**
 * 461. 汉明距离
 * 两个整数之间的 汉明距离 指的是这两个数字对应二进制位不同的位置的数目。
 * 给你两个整数 x 和 y，计算并返回它们之间的汉明距离。
 * 注：先对两数做异或运算，然后对结果进行比特位计数即可
 *
 * 示例 1：
 * 输入：x = 1, y = 4
 * 输出：2
 * 解释：
 * 1   (0 0 0 1)
 * 4   (0 1 0 0)
 *        ↑   ↑
 * 上面的箭头指出了对应二进制位不同的位置。
 *
 * 示例 2：
 * 输入：x = 3, y = 1
 * 输出：1
 */
public class HammingDistance {
    /*
      注：计算 x 和 y 之间的汉明距离，可以先计算 x ⊕ y，然后统计结果中等于 1 的位数。
         原始问题转换为位计数问题
     */
    /**
     * 方法1：内置位计数功能
     */
    public int hammingDistance(int x, int y) {
        return Integer.bitCount(x ^ y);
    }

    /**
     * 方法2：移位实现位计数
     */
    public int hammingDistance2(int x, int y) {
        int s = x ^ y, ret = 0;
        while (s != 0) {
            ret += (s & 1);
            s >>= 1;
        }
        return ret;
    }

    /**
     * 方法3：Brian Kernighan 算法
     */
    public int hammingDistance3(int x, int y) {
        int s = x ^ y, ret = 0;
        while (s != 0) {
            s &= (s - 1);
            ret++;
        }
        return ret;
    }
}
