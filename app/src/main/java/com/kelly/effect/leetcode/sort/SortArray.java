package com.kelly.effect.leetcode.sort;

import java.util.Random;

/**
 * 912. 排序数组
 * 给你一个整数数组 nums，请你将该数组升序排列。
 *
 * 示例 1：
 * 输入：nums = [5,2,3,1]
 * 输出：[1,2,3,5]
 *
 * 示例 2：
 * 输入：nums = [5,1,1,2,0,0]
 * 输出：[0,0,1,1,2,5]
 */
class SortArray {

    public int[] sortArray(int[] nums) {
        randomizedQuicksort(nums, 0, nums.length - 1);
        return nums;
    }

    /**
     * 方法一：快速排序
     * 快速排序的主要思想是通过划分将待排序的序列分成前后两部分，其中前一部分的数据都比后一部分的数据要小，
     * 然后再递归调用函数对两部分的序列分别进行快速排序，以此使整个序列达到有序。
     */
    public void randomizedQuicksort(int[] nums, int l, int r) {
        if (l < r) {
            // 随机选择一个数作为基准值，也可直接选择一个数，比如选择第一个元素，见SortTest.java中的快排解法
            int pos = randomizedPartition(nums, l, r);
            randomizedQuicksort(nums, l, pos - 1);
            randomizedQuicksort(nums, pos + 1, r);
        }
    }

    public int randomizedPartition(int[] nums, int l, int r) {
        // 在nums中随机选择一个基准值
        int i = new Random().nextInt(r - l + 1) + l;
        // 把该基准值交换到最后
        swap(nums, r, i);
        return partition(nums, l, r);
    }

    // nums = [5,1,1,2,0,0]
    public int partition(int[] nums, int l, int r) {
        int pivot = nums[r];
        int i = l;
        for (int j = l; j <= r - 1; ++j) {
            if (nums[j] <= pivot) {
                swap(nums, i, j);
                i++;
            }
        }
        swap(nums, i, r);
        return i;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // ------------------分隔线--------------------

    /**
     * 方法二：堆排序
     * 堆排序的思想就是先将待排序的序列建成大根堆，使得每个父节点的元素大于等于它的子节点。
     * 此时整个序列最大值即为堆顶元素，我们将其与末尾元素交换，使末尾元素为最大值，
     * 然后再调整堆顶元素使得剩下的 n-1 个元素仍为大根堆，再重复执行以上操作我们即能得到一个有序的序列。
     */
    public void heapSort(int[] nums) {
        int len = nums.length - 1;
        // 构建大根堆
        buildMaxHeap(nums, len);
        for (int i = len; i >= 1; --i) {
            swap(nums, i, 0);
            len -= 1;
            maxHeapify(nums, 0, len);
        }
    }

    public void buildMaxHeap(int[] nums, int len) {
        // nums = [4, 6, 8, 5, 9]
        for (int i = len / 2; i >= 0; --i) {
            maxHeapify(nums, i, len);
        }
    }

    public void maxHeapify(int[] nums, int i, int len) {
        for (; (i << 1) + 1 <= len;) {
            int lson = (i << 1) + 1;
            int rson = (i << 1) + 2;
            int large;
            if (lson <= len && nums[lson] > nums[i]) {
                large = lson;
            } else {
                large = i;
            }
            if (rson <= len && nums[rson] > nums[large]) {
                large = rson;
            }
            if (large != i) {
                swap(nums, i, large);
                i = large;
            } else {
                break;
            }
        }
    }


    // ------------------分隔线--------------------

    int[] tmp;

    public int[] sortArray3(int[] nums) {
        tmp = new int[nums.length];
        mergeSort(nums, 0, nums.length - 1);
        return nums;
    }

    /**
     * 方法三：归并排序
     * 归并排序利用了分治的思想来对序列进行排序。对一个长为 n 的待排序的序列，将其分解成两个长度为 n/2 的子序列。
     * 每次先递归调用函数使两个子序列有序，然后我们再线性合并两个有序的子序列使整个序列有序。
     */
    public void mergeSort(int[] nums, int l, int r) {
        if (l >= r) {
            return;
        }
        int mid = (l + r) >> 1;
        mergeSort(nums, l, mid);
        mergeSort(nums, mid + 1, r);
        int i = l, j = mid + 1;
        int cnt = 0;
        while (i <= mid && j <= r) {
            if (nums[i] <= nums[j]) {
                tmp[cnt++] = nums[i++];
            } else {
                tmp[cnt++] = nums[j++];
            }
        }
        while (i <= mid) {
            tmp[cnt++] = nums[i++];
        }
        while (j <= r) {
            tmp[cnt++] = nums[j++];
        }
        for (int k = 0; k < r - l + 1; ++k) {
            nums[k + l] = tmp[k];
        }
    }

}
