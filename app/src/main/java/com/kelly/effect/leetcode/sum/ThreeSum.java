package com.kelly.effect.leetcode.sum;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright (c) 2014-2021 Zuoyebang, All rights reserved.
 *
 * @author zongkaili | zongkaili@zuoyebang.com
 * @version 1.0.0 | 2021/8/10 | zongkaili 初始版本
 * @date 2021/8/10 7:35 下午
 * @description 15. 三数之和 等于0
 * 给你一个包含 n 个整数的数组nums，判断nums中是否存在三个元素 a，b，c ，使得a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 *
 * 示例 1：
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 *
 * 示例 2：
 * 输入：nums = []
 * 输出：[]
 *
 * 示例 3：
 * 输入：nums = [0]
 * 输出：[]
 */
public class ThreeSum {

    /**
     * 解法：排序 + 双指针
     * @param nums 指定数组
     * @return 三数之和为0，满足这个条件的组合
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        //先排序，便于后续循环比较
        Arrays.sort(nums);

        int len = nums.length;
        for (int first = 0; first < len; first++) {
            //第二个数和第一个数相同，就跳过
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            //第一个数num[first]，则后面两个数的和应该是 -nums[first]
            int target = -nums[first];
            //第三个数的下标
            int third = len - 1;
            for (int second = first + 1; second < len; second++) {
                //第三个数和第二个数相同，就跳过
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }
                while (second < third && nums[second] + nums[third] > target) {
                    --third;
                }
                //如果third向左移动到second了，因为是有序的，则无需再移动了，可直接退出这一层循环了
                if (second == third) {
                    break;
                }
                if (nums[second] + nums[third] == target) {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[first]);
                    list.add(nums[second]);
                    list.add(nums[third]);
                    ans.add(list);
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("三数之和：" + new Gson().toJson(threeSum(new int[]{-1,0,1,2,-1,-4})));
    }
}
