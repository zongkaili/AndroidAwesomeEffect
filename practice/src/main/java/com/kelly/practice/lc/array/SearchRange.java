package com.kelly.practice.lc.array;

/**
 * 34. 在排序数组中查找元素的第一个和最后一个位置
 * <p>
 * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
 * 如果数组中不存在目标值 target，返回 [-1, -1]。
 * 你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。
 * <p>
 * 示例 1：
 * 输入：nums = [5,7,7,8,8,10], target = 8
 * 输出：[3,4]
 * <p>
 * 示例 2：
 * 输入：nums = [5,7,7,8,8,10], target = 6
 * 输出：[-1,-1]
 * <p>
 * 示例 3：
 * 输入：nums = [], target = 0
 * 输出：[-1,-1]
 */
class SearchRange {

    /**
     * 解法一：双指针（自研）
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * 测试用例：nums = [1], target = 0, 输出[-1,-1]
     */
    public int[] searchRangeOfTargetNumInSortedArray(int[] nums, int target) {
        int i = 0, j = nums.length - 1;
        // 判断极端情况
        if (j == -1) {
            return new int[]{-1, -1};
        } else if (j == 0) {
            if (target == nums[0]) {
                return new int[]{0, 0};
            } else {
                return new int[]{-1, -1};
            }
        }
        // 双指针开始遍历
        boolean searchStart = false, searchEnd = false;
        while ((!searchStart || !searchEnd) && i < j) {
            if (nums[i] == target) {
                searchStart = true;
            } else {
                i++;
            }
            if (nums[j] == target) {
                searchEnd = true;
            } else {
                j--;
            }
        }
        if (i > j) {
            i = -1;
            j = -1;
        }
        return new int[]{i, j};
    }

    /**
     * 解法二：二分查找（官方）
     * <p>
     * 时间复杂度： O(log n) ，其中 n 为数组的长度。二分查找的时间复杂度为 O(log n)，一共会执行两次，因此总时间复杂度为 O(log n)。
     * 空间复杂度：O(1) 。只需要常数空间存放若干变量。
     * </p>
     * 测试用例：nums = [1], target = 0, 输出[-1,-1]
     */
    public int[] searchRangeOfTargetNumInSortedArray2(int[] nums, int target) {
        int leftIdx = binarySearch(nums, target, true);
        int rightIdx = binarySearch(nums, target, false) - 1;
        if (leftIdx <= rightIdx && rightIdx < nums.length && nums[leftIdx] == target && nums[rightIdx] == target) {
            return new int[]{leftIdx, rightIdx};
        }
        return new int[]{-1, -1};
    }

    public int binarySearch(int[] nums, int target, boolean isLower) {
        int left = 0, right = nums.length - 1, ans = nums.length;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] > target || (isLower && nums[mid] >= target)) {
                right = mid - 1;
                ans = mid;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }
}
