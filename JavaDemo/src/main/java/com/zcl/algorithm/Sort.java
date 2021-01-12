package com.zcl.algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * 1.冒泡排序
 * 2.快速排序
 * 3.选择排序
 */
public class Sort {

    public static void main(String[] args) {
//        int[] arr = {52,63,14,59,68,35,8,67,45,99};
//
////        selectSort(arr);
//
//
//
//        for (int i=0; i< arr.length-1;i++) {
//            System.out.println(arr[i]);
//        }


//        int[] arr = {2,3,7,1,5};
//        // 冒泡排序
////        bubble(arr);
//
//        quickSort(arr, 0 , arr.length-1);
//
//        for (int i=0; i< arr.length;i++) {
//            System.out.println(arr[i]);
//        }

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.println(i + "=" + random.nextBoolean());
        }

    }


    /**
     * 1.冒泡排序：
     *
     * 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
     * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。
     * 针对所有的元素重复以上的步骤，直到没有任何一对数字需要比较。
     * @param arr
     * @return
     */
    public static int[] bubble(int[] arr) {
        //控制循排序轮数  这里-1 是因为每一轮都将最大的数排最后，最后一个不需再进行比较了
        for (int i = 0; i < arr.length - 1; i++) {
            //每轮排序 需要比较的元素比上一轮少一个（-i的原因）
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }


    /**
     * 2.快速排序
     * @param arr
     * @param left
     * @param right
     */
    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(arr, left, right);
        //递归基准左侧部分
        quickSort(arr, left, pivot-1);
        //递归基准右侧部分
        quickSort(arr, pivot+1, right);
    }

    private static int partition(int[] arr, int left, int right) {
        // 以最左边的数(left)为基准 这中情况是最糟的情况 调用栈很深  随机选择会使平均速度会快很多
        int base = arr[left];
        while (left < right) {
            // 从序列右端开始，向左遍历，直到找到小于base的数
            while (left < right && arr[right] >= base)
                right--;
            // 找到了比base小的元素，将这个元素放到最左边的位置
            arr[left] = arr[right];

            // 从序列左端开始，向右遍历，直到找到大于base的数
            while (left < right && arr[left] <= base)
                left++;
            // 找到了比base大的元素，将这个元素放到最右边的位置
            arr[right] = arr[left];
        }

        // 最后将base放到left位置。此时，left位置的左侧数值应该都比left小；
        // 而left位置的右侧数值应该都比left大。
        arr[left] = base;
        return left;
    }

    public class QuickSort {

        public int[] sort(int[] sourceArray) throws Exception {
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

        private int partition(int[] arr, int left, int right) {
            // 设定基准值（pivot）
            int pivot = left;
            int index = pivot + 1;
            for (int i = index; i <= right; i++) {
                if (arr[i] < arr[pivot]) {
                    swap(arr, i, index);
                    index++;
                }
            }
            swap(arr, pivot, index - 1);
            return index - 1;
        }

        private void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }


    /**
     * 3.选择排序 O(n2) n的平方
     * 基本思路每次找出未排序最小(大)的数，把该数放在它应在的位置。
     * <p>
     * 可以看出起泡法和选择排序法需要的趟数都是一样的，不同的是交换的次数。起泡法每次比较就要立刻交换，
     * 而选择排序是把未排序最小的数找出来与它应在的位置上的元素交换。
     * 相对来说，选择排序交换次数较少，一定程度上提高了运算效率
     */
    public static void selectSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;

            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }

            if (min != i) {
                swap(arr, i, min);
            }
        }
    }

    private static void swap(int[] arr, int indexI, int indexJ) {
        int temp = arr[indexI];
        arr[indexI] = arr[indexJ];
        arr[indexJ] = temp;
    }



}
