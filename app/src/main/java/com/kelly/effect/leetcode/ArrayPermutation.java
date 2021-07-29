package com.kelly.effect.leetcode;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 数组全排列
 *
 * @author zyb
 */
public class ArrayPermutation {

    public static int count;

    public static void main(String[] args) {
//        permutation(new char[]{'1','2','3'}, 0);

        //第二种解法，效率低于第一种
        List<List<Integer>> list = premute(new int[]{1, 2, 3});
        System.out.println("全排列结果list >>> " + new Gson().toJson(list));
    }

    public static void permutation(char chs[], int start) {
        System.out.println(start + "----" + Arrays.toString(chs) + count++);

        if (start == chs.length - 1) {
            System.out.println(Arrays.toString(chs));
            //如果已经到了数组的最后一个元素，前面的元素已经排好，输出。
        }

        for (int i = start; i <= chs.length - 1; i++) {
            System.out.println("   开始 " + start + "----" + i + "----" + Arrays.toString(chs));
            //把第一个元素分别与后面的元素进行交换，递归的调用其子数组进行排序
            swap(chs, i, start);
            permutation(chs, start + 1);
            swap(chs, i, start);
            System.out.println("   结束 " + start + "----" + i + "----" + Arrays.toString(chs));
            //子数组排序返回后要将第一个元素交换回来。
            //如果不交换回来会出错，比如说第一次1、2交换，第一个位置为2，子数组排序返回后如果不将1、2
            //交换回来第二次交换的时候就会将2、3交换，因此必须将1、2交换使1还是在第一个位置
        }
    }

    public static void swap(char chs[], int i, int start) {
        char temp;
        temp = chs[i];
        chs[i] = chs[start];
        chs[start] = temp;
    }

    public void p(char[] cs, int start) {
        if (start == cs.length) {
            //dayin
        }
        for (int i = start; i <= cs.length; i++) {
            swap(cs, i, start);
            p(cs, start++);
            swap(cs, i, start);
        }
    }


    private static List<List<Integer>> res = new LinkedList<>();

    private static List<List<Integer>> premute(int[] nums) {
        LinkedList<Integer> track = new LinkedList<>();
        backtrack(nums, track);
        return res;
    }

    /**
     * 路径:记录在 track 中
     * 选择列表:nums 中不存在于 track 的那些元素
     * 结束条件:nums 中的元素全都在 track 中出现
     * @param nums
     * @param track
     */
    private static void backtrack(int[] nums, LinkedList<Integer> track) {
        // 触发结束条件
        if (track.size() == nums.length) {
            res.add(new LinkedList<>(track));
            return;
        }

        for (int num : nums) {
            if (track.contains(num)) {
                continue;
            }
            track.add(num);
            //进入下一层决策树
            backtrack(nums, track);
            //取消选择
            track.removeLast();
        }
    }

}

