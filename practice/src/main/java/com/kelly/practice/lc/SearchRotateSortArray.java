package com.kelly.practice.lc;

/**
 * author: zongkaili
 * data: 2022/3/2
 * desc: 33. 搜索旋转排序数组
 * 整数数组 nums 按升序排列，数组中的值 互不相同 。
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，
 * 使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。
 * 例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为[4,5,6,7,0,1,2] 。
 * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回-1。
 *
 * 示例 1：
 * 输入：nums = [4,5,6,7,0,1,2], target = 0
 * 输出：4
 *
 * 示例2：
 * 输入：nums = [4,5,6,7,0,1,2], target = 3
 * 输出：-1
 *
 * 示例 3：
 * 输入：nums = [1], target = 0
 * 输出：-1
 */
class SearchRotateSortArray {
    // nums = [4,5,6,7,0,1,2], target = 0   return 4

    /**
     * 时间复杂度： O(log n)，其中 n 为 nums 数组的大小。
     *            整个算法时间复杂度即为二分查找的时间复杂度 O(log n)。
     * 空间复杂度： O(1)。我们只需要常数级别的空间存放变量。
     */
    public int search(int[] nums, int target) {
        int len = nums.length;
        if (len == 0) {
            return -1;
        }
        if (len == 1) {
            return nums[0] == target ? 0 : -1;
        }
        int l = 0, r = len - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[0] <= nums[mid]) {
                // 左边有序
                if (nums[0] <= target && target < nums[mid]) {
                    // target在左子数组中
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else {
                // 右边有序
                if (nums[mid] < target && target <= nums[len - 1]) {
                    // target在右子数组中
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return -1;
    }
}
