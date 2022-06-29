package com.kelly.effect.leetcode.sum;

/**
 * 53. 最大子数组和
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 子数组 是数组中的一个连续部分。
 *
 * 示例 1：
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组[4,-1,2,1] 的和最大，为6 。
 *
 * 示例 2：
 * 输入：nums = [1]
 * 输出：1
 *
 * 示例 3：
 * 输入：nums = [5,4,-1,7,8]
 * 输出：23
 */
class MaxSubArraySum {

    /**
     * 方法一：动态规划
     */
    public int maxSubArray(int[] nums) {
        int curNum = nums[0];
        int maxSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            curNum = Math.max(nums[i], nums[i] + curNum);
            maxSum = Math.max(curNum, maxSum);
        }
        return maxSum;
    }

    public int maxSubArray2(int[] nums) {
        int maxSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] > 0) {
                nums[i] += nums[i - 1];
            }
            maxSum = Math.max(nums[i], maxSum);
        }
        return maxSum;
    }

    /**
     * 变种：不能选相邻的数字。
     * 假设给定一串数字{1, 2, 4, 1, 7, 8, 3}，我们要从中选择若干个数，使最后的和达到最大。
     * 比如：如果我们选了第一个数字1，那么我们就不能选2，如果我们选择了数字4，那么我们就不能选择与它相邻的2和1。
     */
    public int maxSum(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        int curNum = nums[0];
        int maxSum = nums[0];
        for (int i = 2; i < nums.length; i += 2) {
            curNum = Math.max(nums[i], nums[i] + curNum);
            maxSum = Math.max(curNum, maxSum);
        }
        return maxSum;
    }

}