package com.kelly.practice.lc.sum;

/**
 * author: zongkaili
 * data: 2020/3/31
 * desc: 53. 最大子序和
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * <p>
 * 示例:
 * 输入: [-2,1,-3,4,-1,2,1,-5,4],
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 */
public class SubArrayMaxSum {

    public static void main(String[] args) {
        System.out.println(" result >>> " + maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }

    private static int maxSubArray(int[] nums) {
        //solution1: 分治法的典型示例   todo 没看懂
//        return helper(nums, 0, nums.length - 1);

        //solution2: 贪心
//        int n = nums.length;
//        int curNum = nums[0];
//        int maxSum = nums[0];
//        for (int i = 1; i < n; i++) {
//            curNum = Math.max(nums[i], curNum + nums[i]);
//            maxSum = Math.max(maxSum, curNum);
//        }
//        return maxSum;

        //solution: 动态规划（Kadane 算法）
        int n = nums.length, maxSum = nums[0];
        for(int i = 1; i < n; ++i) {
            if (nums[i - 1] > 0) nums[i] += nums[i - 1];
            maxSum = Math.max(nums[i], maxSum);
        }
        return maxSum;
    }

    private static int helper(int[] nums, int left, int right) {
        if (left == right) return nums[left];

        int p = (left + right) / 2;

        int leftSum = helper(nums, left, p);
        int rightSum = helper(nums, p + 1, right);
        int crossSum = crossSum(nums, left, right, p);

        return Math.max(Math.max(leftSum, rightSum), crossSum);
    }

    private static int crossSum(int[] nums, int left, int right, int p) {
        if (left == right) return nums[left];

        int leftSubSum = Integer.MIN_VALUE;
        int currSum = 0;
        for (int i = p; i > left - 1; --i) {
            currSum += nums[i];
            leftSubSum = Math.max(leftSubSum, currSum);
        }

        int rightSubSum = Integer.MIN_VALUE;
        currSum = 0;
        for (int i = p + 1; i < right + 1; ++i) {
            currSum += nums[i];
            rightSubSum = Math.max(rightSubSum, currSum);
        }

        return leftSubSum + rightSubSum;
    }

}
