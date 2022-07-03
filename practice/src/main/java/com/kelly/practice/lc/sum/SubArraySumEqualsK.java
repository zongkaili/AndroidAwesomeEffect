package com.kelly.practice.lc.sum;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;

/**
 * author: zongkaili
 * data: 2022/7/3
 * desc: 560. 和为 K 的子数组
 * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的子数组的个数。
 * <p>
 * 示例 1：
 * 输入：nums = [1,1,1], k = 2
 * 输出：2
 * <p>
 * 示例 2：
 * 输入：nums = [1,2,3], k = 3
 * 输出：2
 */
class SubArraySumEqualsK {

    /**
     * 方法一：枚举
     * 时间复杂度：O(n^2) 其中 n 为数组的长度。枚举子数组开头和结尾需要 O(n^2) 的时间，
     * 其中求和需要 O(1) 的时间复杂度，因此总时间复杂度为 O(n^2)
     * 空间复杂度：O(1)。只需要常数空间存放若干变量。
     */
    public int subArraySum(int[] nums, int k) {
        int count = 0;
        for (int start = 0; start < nums.length; ++start) {
            int sum = 0;
            for (int end = start; end >= 0; --end) {
                sum += nums[end];
                if (sum == k) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 方法二：前缀和 + 哈希表优化
     * 时间复杂度：O(n)，其中 n 为数组的长度。我们遍历数组的时间复杂度为 O(n)，中间利用哈希表查询删除的复杂度均为 O(1)，因此总时间复杂度为 O(n)。
     * 空间复杂度：O(n)，其中 n 为数组的长度。哈希表在最坏情况下可能有 n 个不同的键值，因此需要 O(n) 的空间复杂度。
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public int subArraySum2(int[] nums, int k) {
        // 统计和为 K 的子数组的数量
        int count = 0;
        // 记录遍历到索引为 i 的这个元素时，前缀和的值是多少
        int pre = 0;
        // 利用哈希表，以前缀和为键，出现次数为对应的值，记录 pre[i] 出现的次数
        HashMap<Integer, Integer> mp = new HashMap<>();
        // 作用：为了应对 nums[0] +nums[1] + ... + nums[i] == k 这种情况
        mp.put(0, 1);
        /*
         1、存储索引为 i 的这个元素时，前缀和的值是多少，并且把这个值出现的频次存储到 mp 中
         2、判断之前有没有存储 pre - k 这种前缀和，如果有，说明 pre - k 到 pre 直接的那些元素值之和就是 k
         */
        for (int num : nums) {
            pre += num;
            if (mp.containsKey(pre - k)) {
                count += mp.get(pre - k);
            }
            mp.put(pre, mp.getOrDefault(pre, 0) + 1);
        }
        return count;
    }
}
