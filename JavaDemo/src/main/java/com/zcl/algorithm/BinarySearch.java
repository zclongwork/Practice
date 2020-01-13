package com.zcl.algorithm;

/**
 * 二分查找 O（log n） 简单（顺序）查找 O（n）
 * 基本思路是：首先确定该查找区间的中间点位置： int mid = (low+upper) / 2；
 * 然后将待查找的值与中间点位置的值比较：
 * 若相等，则查找成功并返回此位置。
 * 若中间点位置值大于待查值，则新的查找区间是中间点位置的左边区域。
 * 若中间点位置值小于待查值，则新的查找区间是中间点位置的右边区域。
 * 下一次查找是针对新的查找区间进行的。
 *
 * 顺序查找效率较低
 * 二分查找效率高，但是要在序列是在有序的情况下
 *
 */
public class BinarySearch {


    public static void main(String[] args) {

        int [] arr = {0,1,2,3,4,5,6,7,8,9,10};

        int i = binarySearch(arr, 7);
        System.out.println("7 在数组中的角标 index = " + i);

    }


    //二分查找算法
    public static int binarySearch(int[] arr,int num) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int mid = (high + low) / 2;
            if (arr[mid] < num) {
                low = mid + 1;
            } else if (arr[mid] > num) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
