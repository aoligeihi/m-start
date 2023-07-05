package com.yy.star.demo.java;

public class ToSort {
    public static void main(String[] args) {
        int[] arr = {1, 2, 6, 3, 4, 9, 0};
        System.out.println(biSearch(arr, 9));
    }

    /**
     * 二分查找,又叫折半查找,要求待查找的序列有序。每次取中间位置的值与待查关键字比较，如果中间位置
     * 的值比待查关键字大，则在前半部分循环这个查找的过程，如果中间位置的值比待查关键字小，
     * 则在后半部分循环这个查找的过程。直到查找到了为止，否则序列中没有待查的关键字。
     *
     * @param array
     * @param a
     * @return
     */
    public static int biSearch(int[] array, int a) {
        int lo = 0;
        int hi = array.length - 1;
        int mid;
        while (lo <= hi) {
            mid = (lo + hi) / 2;//中间位置
            if (array[mid] == a) {
                return mid + 1;
            } else if (array[mid] < a) { //向右查找
                lo = mid + 1;
            } else { //向左查找
                hi = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 冒泡排序
     * 1.比较前后相邻的二个数据，如果前面数据大于后面的数据，就将这二个数据交换。
     * 2.这样对数组的第 0 个数据到 N-1 个数据进行一次遍历后，最大的一个数据就“沉”到数组第
     * N-1 个位置。
     * 3.N=N-1，如果 N 不为 0 就重复前面二步，否则排序完成
     *
     * @param a
     * @param n
     */
    public static void bubbleSort1(int[] a, int n) {
        int i, j;
        for (i = 0; i < n; i++) {//表示 n 次排序过程。
            for (j = 1; j < n - i; j++) {
                if (a[j - 1] > a[j]) {//前面的数字大于后面的数字就交换
                    //交换 a[j-1]和 a[j]
                    int temp;
                    temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                }
            }
        }
    }
}
