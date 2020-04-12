package com.kelly.effect.leetcode;

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
//        insertSort(arr);
        quicksort(arr, 0, arr.length - 1);

        for (int a : arr) {
            System.out.println(a);
        }
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
    }

    /**
     * 快速排序
     * @param arr
     * @param begin
     * @param end
     */
    private static void quicksort(int[] arr, int begin, int end) {
        int a = begin;
        int b = end;
        if (a >= b) {
            return;
        }
        int x = arr[a];//基准数,默认设置为第一个值
        while (a < b) {
            //从后往前找,找到一个比基准数x小的值,赋给arr[a]
            //如果a和b的逻辑正确--a<b ,并且最后一个值arr[b]>x,就一直往下找,直到找到arr[b]小于x
            while (a < b && arr[b] >= x) {
                b--;
            }
            //跳出循环,两种情况,一是a和b的逻辑不对了,a>=b,这时候排序结束.二是在后面找到了比x小的值
            if (a < b) {
                arr[a] = arr[b];//将这时候找到的arr[b]放到最前面arr[a]
                a++;//排序的起始位置后移一位
            }

            //从前往后找,找到一个比基准数x大的值,放在最后面arr[b]
            while (a < b && arr[a] <= x) {
                a++;
            }
            if (a < b) {
                arr[b] = arr[a];
                //排序的终止位置前移一位
                b--;
            }
        }
        //跳出循环 a < b的逻辑不成立了,a==b重合了,此时将x赋值回去arr[a]
        arr[a] = x;
        //调用递归函数,再细分再排序
        quicksort(arr, begin, a - 1);
        quicksort(arr, a + 1, end);
    }
}
