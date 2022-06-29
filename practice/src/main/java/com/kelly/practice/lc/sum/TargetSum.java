package com.kelly.practice.lc.sum;

/**
 * Copyright (c) 2014-2021 Zuoyebang, All rights reserved.
 *
 * @author zongkaili | zongkaili@zuoyebang.com
 * @version 1.0.0 | 2021/8/10 | zongkaili 初始版本
 * @date 2021/8/10 10:37 上午
 * @description 494. 目标和
 * 给你一个整数数组 nums 和一个整数 target 。
 * 向数组中的每个整数前添加'+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
 * <p>
 * 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
 * 返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
 * <p>
 * 示例 1：
 * 输入：nums = [1,1,1,1,1], target = 3
 * 输出：5
 * 解释：一共有 5 种方法让最终目标和为 3 。
 * -1 + 1 + 1 + 1 + 1 = 3
 * +1 - 1 + 1 + 1 + 1 = 3
 * +1 + 1 - 1 + 1 + 1 = 3
 * +1 + 1 + 1 - 1 + 1 = 3
 * +1 + 1 + 1 + 1 - 1 = 3
 * <p>
 * 示例 2：
 * 输入：nums = [1], target = 1
 * 输出：1
 */
public class TargetSum {
    static int count = 0;

    /**
     * todo
     * 在给定数组中一连串数字之间添加"+"和"-"组成表达式，
     * 求结果等于目标数target的表达式的个数
     *
     * @param nums   给定数组
     * @param target 目标和
     * @return 给定数组中添加"+"和"-"后，满足目标和等于target的表达式的个数
     */
    public static int findTargetSumWays(int[] nums, int target) {
        backtrack(nums, target, 0, 0);
        return count;
    }

    /**
     * 解法1：回溯
     *
     * @param nums   给定数组
     * @param target 目标数
     * @param index  数组下标
     * @param sum    当前元素和
     */
    public static void backtrack(int[] nums, int target, int index, int sum) {
        if (index == nums.length) {
            if (sum == target) {
                count++;
            }
        } else {
            backtrack(nums, target, index + 1, sum + nums[index]);
            backtrack(nums, target, index + 1, sum - nums[index]);
        }
    }

    /**
     * 解法2：动态规划
     */
    public static void backtrack() {

    }

    public static void main(String[] args) {
        System.out.println("目标和：" + findTargetSumWays(new int[]{1, 1, 1, 1, 1}, 3));
    }

}
