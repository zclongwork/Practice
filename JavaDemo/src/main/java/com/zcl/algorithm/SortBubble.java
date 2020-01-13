package com.zcl.algorithm;

/**
 * 冒泡排序
 *
 * 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
 * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。
 * 针对所有的元素重复以上的步骤，直到没有任何一对数字需要比较。
 */
public class SortBubble {


    public static void main(String[] args) {

        int[] arr = {25, 65, 14, 16, 15, 4, 8, 61, 51, 54, 51};

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }





}
