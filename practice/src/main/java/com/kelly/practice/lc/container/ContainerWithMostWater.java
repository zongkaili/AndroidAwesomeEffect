package com.kelly.practice.lc.container;

/**
 * author: zongkaili
 * data: 2022/7/3
 * desc: 11. 盛最多水的容器
 * 给定一个长度为 n 的整数数组 height。有 n 条垂线，第 i 条线的两个端点是(i, 0)和(i, height[i])。
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * 返回容器可以储存的最大水量。
 * 说明：你不能倾斜容器。
 * <p>
 * 示例 1：
 * 输入：[1,8,6,2,5,4,8,3,7]
 * 输出：49
 * 解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水的最大值为49。
 * <p>
 * 示例 2：
 * 输入：height = [1,1]
 * 输出：1
 */
class ContainerWithMostWater {
    /**
     * 方法一：双指针
     * 时间复杂度：O(n)，双指针总计最多遍历整个数组一次。
     * 空间复杂度：O(1)，只需要额外的常数级别的空间。
     */
    public int maxArea(int[] height) {
        int l = 0, r = height.length - 1;
        int area, ans = 0;
        while (l < r) {
            area = Math.min(height[l], height[r]) * (r - l);
            ans = Math.max(ans, area);
            if (height[l] <= height[r]) {
                ++l;
            } else {
                --r;
            }
        }

        // 下面这种写法更好一点
//        int h;
//        while (l < r) {
//            h = Math.min(height[l], height[r]);
//            ans = Math.max(ans, h * (r - l));
//            while (height[l] <= h && l < r) ++l;
//            while (height[r] <= h && l < r) --r;
//        }

        return ans;
    }

    /**
     * 方法二：双层for循环
     * 时间复杂度：O(n^2)。
     * 空间复杂度：O(1)。
     */
    public int maxArea2(int[] height) {
        int max = 0, result;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                result = Math.min(height[i], height[j]) * (j - i);
                if (result > max) {
                    max = result;
                }
            }
        }
        return max;
    }

    public int maxArea3(int[] height) {
        int l = 0, r = height.length - 1;
        int h, max = 0;
        while (l < r) {
            h = Math.min(height[l], height[r]);
            max = Math.max(max, h * (r - l));
            while (height[l] <= h && l < r) ++l;
            while (height[r] <= h && l < r) --r;
        }
        return max;
    }

}
