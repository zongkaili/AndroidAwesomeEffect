package com.kelly.practice.lc.houserobber;

/**
 * Copyright (c) 2014-2021 Zuoyebang, All rights reserved.
 *
 * @author zongkaili | zongkaili@zuoyebang.com
 * @version 1.0.0 | 2021/8/6 | zongkaili 初始版本
 * @date 2021/8/6 9:11 下午
 * @description 213. 打家劫舍 II
 * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。
 * 同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。
 * <p>
 * 示例1：
 * 输入：nums = [2,3,2]
 * 输出：3
 * 解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
 * <p>
 * 示例 2：
 * 输入：nums = [1,2,3,1]
 * 输出：4
 * 解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
 * 偷窃到的最高金额 = 1 + 3 = 4 。
 * <p>
 * 示例 3：
 * 输入：nums = [0]
 * 输出：0
 */
public class HouseRobber2 {
    /**
     * 解法：动态规划 状态&选择
     */
    public static int rob(int[] nums) {
        if (nums == null || nums.length <= 0) {
            return 0;
        }
        if (nums.length == 1) {
            //如果只有一间房，则最大收益就只能是偷这间房
            return nums[0];
        } else if (nums.length == 2) {
            //如果有两间房，则偷钱最多的那间房
            return Math.max(nums[0], nums[1]);
        }
        /*
        如果超过两间房，则有两种情况，在这两种情况中寻找最大收益：
          a.偷了第一间房，则不能偷最后一间房：即偷取范围为第一间到倒数第二间
          b.不偷第一间房，则可能偷最后一间房：即偷取范围为第二间到最后一间
         */
        return Math.max(robRange(nums, 0, nums.length - 2), robRange(nums, 1, nums.length - 1));
    }

    public static int robRange(int[] nums, int start, int end) {
        int first = nums[start];
        int second = Math.max(nums[start], nums[start + 1]);
        for (int i = start + 2; i <= end; i++) {
            int tmp = second;
            second = Math.max(first + nums[i], second);
            first = tmp;
        }
        return second;
    }


    public static void main(String[] args) {
        System.out.println("打家劫舍的最大收益II：" + rob(new int[]{2, 3, 2}));
    }
}
