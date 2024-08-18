package com.kelly.practice.lc.array;

/**
 * author: zongkaili
 * data: 2022/4/19
 * desc: 704. 二分查找
 * 给定一个n个元素有序的（升序）整型数组nums 和一个目标值target ，
 * 写一个函数搜索nums中的 target，如果目标值存在返回下标，否则返回 -1。
 * <p>
 * 示例 1:
 * 输入: nums = [-1,0,3,5,9,12], target = 9
 * 输出: 4
 * 解释: 9 出现在 nums 中并且下标为 4
 * <p>
 * 示例2:
 * 输入: nums = [-1,0,3,5,9,12], target = 2
 * 输出: -1
 * 解释: 2 不存在 nums 中因此返回 -1
 */
final class BinarySearch {
    /**
     * 时间复杂度：O(log n)，其中 n 是数组的长度。
     * 空间复杂度：O(1)。
     */
    public int search(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int midP = (high - low) / 2 + low;
            int midNum = nums[midP];
            if (midNum == target) {
                return midP;
            } else if (midNum < target) {
                low = midP + 1;
            } else {
                high = midP - 1;
            }
        }
        return -1;
    }
}
