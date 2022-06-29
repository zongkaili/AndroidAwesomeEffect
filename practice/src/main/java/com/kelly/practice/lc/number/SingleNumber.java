package com.kelly.practice.lc.number;

import java.util.HashSet;
import java.util.Set;

/**
 * 136. 只出现一次的数字
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 * 说明：你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
 *
 * 示例 1:
 * 输入: [2,2,1]
 * 输出: 1
 *
 * 示例2:
 * 输入: [4,1,2,1,2]
 * 输出: 4
 */
public class SingleNumber {

    /**
     * 方法一：数学求和法
     */
    public int singleNumber(int[] nums) {
        Set<Integer> numSet = new HashSet<>();
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            numSet.add(nums[i]);
            sum += nums[i];
        }
        int setSum = 0;
        for (Integer num : numSet) {
            setSum += num * 2;
        }
        return setSum - sum;
    }

    /**
     * 方法二：异或运算
     * 对于这道题，可使用异或运算 ⊕。
     * 异或运算有以下几个性质。
     * 0.二进制下，如果a、b两个值不相同，则异或结果为1。如果a、b两个值相同，异或结果为0
     * 1.任何数和 00 做异或运算，结果仍然是原来的数，即 a ⊕ 0 = a。
     * 2.任何数和其自身做异或运算，结果是0，即 a ⊕ a = 0。
     * 3.异或运算满足交换律和结合律，即 a ⊕ b ⊕ a = b ⊕ a ⊕ a = b ⊕ (a ⊕ a) = b ⊕ 0 = b。
     */
    public int singleNumber2(int[] nums) {
        int single = 0;
        for (int num: nums) {
            single ^= num;
        }
        return single;
    }
}
