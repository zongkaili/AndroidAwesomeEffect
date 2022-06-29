package com.kelly.practice.lc.sum;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright (c) 2014-2021 Zuoyebang, All rights reserved.
 *
 * @author zongkaili | zongkaili@zuoyebang.com
 * @version 1.0.0 | 2021/8/10 | zongkaili 初始版本
 * @date 2021/8/10 9:33 下午
 * @description 18. 四数之和
 * 给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。
 * 请你找出并返回满足下述全部条件且不重复的四元组[nums[a], nums[b], nums[c], nums[d]] ：
 * 0 <= a, b, c, d< n
 * a、b、c 和 d 互不相同
 * nums[a] + nums[b] + nums[c] + nums[d] == target
 * 你可以按 任意顺序 返回答案 。
 * <p>
 * 示例 1：
 * 输入：nums = [1,0,-1,0,-2,2], target = 0
 * 输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 * <p>
 * 示例 2：
 * 输入：nums = [2,2,2,2,2], target = 8
 * 输出：[[2,2,2,2]]
 */
public class FourSum {
    /**
     * 解法：排序 + 双指针
     * @param nums 数字列表
     * @param target 目标和
     * @return 满足条件的数字列表集合
     */
    private static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums == null || nums.length < 4) {
            return ans;
        }
        //先排序，便于后续循环比较
        Arrays.sort(nums);
        int len = nums.length;
        for (int first = 0; first < len - 3; first++) {
            //跳过相同的元素
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            //因为是排序数组，确定了第一个元素后，前四个元素之和都比target大，那后面的元素就不用加了，加上肯定比target还要大
            if (nums[first] + nums[first + 1] + nums[first + 2] + nums[first + 3] > target) {
                break;
            }
            //因为是排序数组，确定了第一个元素后，和后三个元素之和比target小，那就直接进入第二步循环
            if (nums[first] + nums[len - 3] + nums[len - 2] + nums[len - 1] < target) {
                continue;
            }
            for (int second = first + 1; second < len - 2; second++) {
                //跳过相同的元素
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }
                if (nums[first] + nums[second] + nums[second + 1] + nums[second + 2] > target) {
                    break;
                }
                if (nums[first] + nums[second] + nums[len - 2] + nums[len - 1] < target) {
                    continue;
                }
                int third = second + 1, four = len - 1;
                while (third < four) {
                    int sum = nums[first] + nums[second] + nums[third] + nums[four];
                    if (sum == target) {
                        ans.add(Arrays.asList(nums[first], nums[second], nums[third], nums[four]));
                        while (third < four && nums[third] == nums[third + 1]) {
                            third++;
                        }
                        third++;
                        while (third < four && nums[four] == nums[four - 1]) {
                            four--;
                        }
                        four--;
                    } else if (sum < target) {
                        third++;
                    } else {
                        four--;
                    }
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("四数之和：" + new Gson().toJson(fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0)));
    }
}
