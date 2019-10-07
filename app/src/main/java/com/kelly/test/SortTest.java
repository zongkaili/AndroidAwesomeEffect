package com.kelly.test;

/**
 * author: zongkaili
 * data: 2018/10/14
 * desc:
 */
public class SortTest {
    private static int[] arr = {47, 45, 67, 32, 54, 11, 4, 6, 90, 43, 65, 76, 88, 99};

    public static void main(String[] arg) {
//        bubbleSort(arr);
//        selectSort(arr);
        insertSort(arr);
    }

    /**
     * 冒泡排序
     * 基本思想：
     * 在要排序的一组数中，对当前还未排好序的范围内的全部数，自上而下对相邻的两个数依次进行比较和调整，
     * 让较大的数往下沉，较小的往上冒。即：每当两相邻的数比较后发现它们的排序与排序要求相反时，就将它们互换。
     *
     * @param arr
     */
    private static void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int temp;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        for (int a : arr) {
            System.out.println(a);
        }
    }

    /**
     * 选择排序
     * 基本思想：
     * 在要排序的一组数中，选出最小的一个数与第一个位置的数交换；
     * 然后在剩下的数当中再找最小的与第二个位置的数交换，如此循环到倒数第二个数和最后一个数比较为止。
     *
     * @param arr
     */
    private static void selectSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int pos, temp;
        for (int i = 0; i < arr.length; i++) {
            pos = i;
            temp = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    arr[i] = arr[j];
                    pos = j;
                }
            }
            arr[pos] = temp;
        }

        for (int a : arr) {
            System.out.println(a);
        }
    }

    /**
     * 插入排序
     * 基本思想：
     * 在要排序的一组数中，假设前面(n-1) [n>=2] 个数已经是排好顺序的，现在要把第n个数插到前面的有序数中，
     * 使得这n个数也是排好顺序的。如此反复循环，直到全部排好顺序。
     *
     * @param arr
     */
    private static void insertSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int temp = 0;
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            temp = arr[i];
            for (; j >= 0 && temp < arr[j]; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = temp;
        }
        for (int a : arr) {
            System.out.println(a);
        }
    }
}
