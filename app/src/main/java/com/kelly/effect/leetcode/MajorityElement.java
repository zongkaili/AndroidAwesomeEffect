package com.kelly.effect.leetcode;

import java.util.Random;

/**
 * author: zongkaili
 * data: 2020/3/29
 * desc: 169. 多数元素
 * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 *
 * 示例 1:
 * 输入: [3,2,3]
 * 输出: 3
 *
 * 示例 2:
 * 输入: [2,2,1,1,1,2,2]
 * 输出: 2
 */
public class MajorityElement {
    public static void main(String[] args) {
        System.out.println("输出 ： " + majorityElement(new int[]{6,5,5}));
    }

    private static int majorityElement(int[] nums) {
        //solution1: 粗暴解法
//        if (nums == null || nums.length == 0) return -1;
//        int n = nums.length / 2;
//        int max = 0;
//        int key = 0, value;
//        Map<Integer, Integer> map = new HashMap<>();
//        for (int num : nums) {
//            if (map.containsKey(num)) {
//                if (map.get(num) != null && map.get(num) > n) {
//                    return num;
//                }
//                value = 1 + map.get(num);
//            } else {
//                value = 1;
//            }
//            map.put(num, value);
//            if (value > max) {
//                max = value;
//                key = num;
//            }
//        }
//        return key;

        //solution2: 排序法
//        Arrays.sort(nums);
//        return nums[nums.length/2];

        //solution3: 随机化
//        Random rand = new Random();
//        int majorityCount = nums.length / 2;
//        while (true) {
//            int candidate = nums[randIndex(rand, 0, nums.length)];
//            if (occurenceCount(nums, candidate) > majorityCount) {
//                return candidate;
//            }
//        }

        //solution4: Boyer-Moore 投票算法
        int count = 0;
        Integer candidate = null;
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += num == candidate ? 1 : -1;
        }
        return candidate;
    }

    private static int occurenceCount(int[] nums, int candidate) {
        int count = 0;
        for (int num : nums) {
            if (num == candidate) {
                count++;
            }
        }
        return count;
    }

    private static int randIndex(Random random, int min, int max) {
        return random.nextInt(max - min) + min;
    }
}
