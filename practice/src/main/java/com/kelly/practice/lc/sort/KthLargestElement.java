package com.kelly.practice.lc.sort;

/**
 * 215. 数组中的第K个最大元素
 * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
 * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 *
 * 示例 1:
 * 输入: [3,2,1,5,6,4] 和 k = 2
 * 输出: 5
 *
 * 示例2:
 * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
 * 输出: 4
 */
class KthLargestElement {
    /**
     * 方法：先排序，然后输出倒数第k个元素
     */
    public int findKthLargest(int[] nums, int k) {
        sort(nums);
        return nums[nums.length - k];
    }

    private void sort(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
    }

    /**
     * 快速排序
     */
    private void quickSort(int[] nums, int begin, int end) {
        // arr = {47, 45, 67, 32, 54, 11, 4, 6, 90, 43, 65, 76, 88, 99};
        int a = begin;
        int b = end;
        if (a >= b) return;
        int x = nums[a]; // 默认取第一个数为基准值
        while (a < b) {
            while (a < b && nums[b] >= x) {
                b--;
            }
            if (a < b) {
                nums[a] = nums[b];
                a++;
            }
            while (a < b && nums[a] <= x) {
                a++;
            }
            if (a < b) {
                nums[b] = nums[a];
                b--;
            }
        }
        nums[a] = x;
        quickSort(nums, begin, a - 1);
        quickSort(nums, a + 1, end);
    }
}
