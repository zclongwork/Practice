package com.zcl.algorithm;

import java.util.Arrays;

/**
 * 快速排序 ：思想分治
 * 选取基准元素，小于基准元素的放到左侧，大于基准元素的放到右测
 * 对上边两侧元素，进行上方重复操作 （递归）
 *
 * Author zcl
 * Date 2021/10/25
 */
class QuickSort {

    public static void main(String[] args) {
        int[] arr = {5,3,4,9,7,6};
//        int[] arr = {5,3,9,4,6};
        QuickSort sort = new QuickSort();
        int[] result = sort.sort(arr);


        System.out.println("");
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + ",");
        }
    }

    public int[] sort(int[] sourceArray){
        // 对 arr 进行拷贝，不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
        return quickSort(arr, 0, arr.length - 1);
    }

    private int[] quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int partitionIndex = partition(arr, left, right);
            quickSort(arr, left, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, right);
        }
        return arr;
    }

    /**
     * 将数组元素进行分区（小的在基准左侧，大的在基准右侧），并返回基准值索引
     * 思路：双指针
     * 选取第0个元素为基准值
     * 1.指针pointer 用于标示出第一个大于基准值的元素
     *      指针pointer元素小于基准值时 指针pointer右移
     * 2.指针i遍历元素 元素小于基准值时 与指针pointer元素交换
     *
     * @param arr 数组
     * @param left 左边界
     * @param right 右边界
     * @return 基准值在数组中的索引
     */
    private int partition(int[] arr, int left, int right) {
        // 设定基准值（pivot）的索引
        int pivot = left;

        //指针 指针所在位置值< 基准值 时，指针右移动 for循环的i相当于另外一个指针
        int pointer = pivot + 1;
        for (int i = pointer; i <= right; i++) {
            System.out.print("-- i=" +i + " index=" + pointer + "    ");
            //值如果小于基准值 交换（代表 移到左侧）
            if (arr[i] < arr[pivot]) {
                if (i > pointer) {
                    swap(arr, i, pointer);
                }
                pointer++;
            }

            //日志
            arrPrint(arr);
        }
        swap(arr, pivot, pointer - 1);

        //日志
        arrPrint(arr);

        return pointer - 1;
    }

    private void swap(int[] arr, int i, int j) {

        System.out.print("swap i=" +i + " j=" + j + "  ");

        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 打印数组
     * @param arr
     */
    private void arrPrint(int[] arr) {
        PrintUtil.arrPrint(arr);
    }


}
