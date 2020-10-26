package com.zcl.algorithm;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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

//        int [] arr = {0,1,2,3,4,5,6,7,8,9,10};
//
//        int i = binarySearch(arr, 7);


        String content = sort("anagram");
        System.out.println("sort: " + content);

//        System.out.println(withOutSpace("db##"));
//        System.out.println(mySqrt(4));


        System.out.println(('c'-'a'));
    }

    private static String sort(String content) {
        char[] arr = content.toCharArray();
        Arrays.sort(arr);
        return Arrays.toString(arr);
    }

    public int[] intersection(int[] nums1, int[] nums2) {
        HashSet<Integer> set1 = new HashSet();
        for(Integer i:nums1 ) {
            set1.add(i);
        }
        HashSet<Integer> set2 = new HashSet();
        for(Integer i:nums2 ) {
            set2.add(i);
        }
        if (set1.size() < set2.size()) {
            return merge(set2, set1);
        } else {
            return merge(set1, set2);
        }
    }

    private int[] merge(HashSet<Integer> set1, HashSet<Integer> set2) {
        HashSet<Integer> result = new HashSet<>();
        Iterator<Integer> it = set2.iterator();
        while (it.hasNext()) {
            if (set1.contains(it.next())) {
                result.add(it.next());
            }
        }

        int[] arr = new int[result.size()];
        Iterator<Integer> rit = set2.iterator();
        int i=0;
        while (rit.hasNext()) {
            arr[i] = rit.next();
            i++;
        }

        return arr;
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

    private static int search(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        int pos = -1;
        while (left <= right) {
            int mid = ((right - left) >> 1) + left;
            if (numbers[mid] < target) {
                left = mid + 1;
            } else if (numbers[mid] > target) {
                right = mid - 1;
            } else {
                pos = mid;
                return pos;
            }
        }
        return pos;
    }

    /**
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     *
     * 二分查找条件转换：查找第一个大于等于目标元素索引
     *
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearch2(int[] nums,int target) {
        int n = nums.length;
        int left = 0, right = n - 1, pos = n;
        while (left <= right) {
            //右移1位相当于除2
            int mid = ((right - left) >> 1) + left;
            if (target <= nums[mid]) {
                pos = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return pos;
    }

    public static int mySqrt(int x) {
        int left =0;
        int right = x;
        int pos = x;
        while(left <= right) {
            int mid = ((right-left)>>1) + left;
            if ((long)mid * (long)mid <= x) {
                pos = mid;
                left = mid + 1;
            } else {
                right = mid -1;
            }
        }
        return pos;


//        int l = 0, r = x, ans = -1;
//        while (l <= r) {
//            int mid = l + (r - l) / 2;
//            if (mid * mid <= x) {
//                ans = mid;
//                l = mid + 1;
//            } else {
//                r = mid - 1;
//            }
//        }
//        return ans;
    }


    //获取删除字符串
    private static String withOutSpace(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] arr = s.toCharArray();

        int space = 0;
        for (int i = arr.length-1;i>=0;i--) {
            if ('#'== arr[i]) {
                space++;
                continue;
            }
            if (space > 0) {
                space--;
                continue;
            }

            stringBuilder.append(arr[i]);

        }
        return stringBuilder.toString();
    }
}
