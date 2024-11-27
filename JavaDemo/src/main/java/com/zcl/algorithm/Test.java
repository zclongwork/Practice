package com.zcl.algorithm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {


    public static void main(String[] args) {
//        int[] nums1 = {1,2,3,9,0,0,0};
//        int[] nums2 = {2,5,6};
//        int[] nums1 = {0};
//        int[] nums2 = {1};
//        merge(nums1, 0, nums2, 1);
        String s = "er中文er";

        System.out.println( s +" 内容石否包含中文=" + containsChinese(s));
    }

    public static boolean containsChinese(String str) {

        if (str == null) {
            return false;
        }
        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+");
        Matcher m = p.matcher(str);
        return m.find();
//        return s.matches("[\\u4E00-\\u9FA5]+");
    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m-1;
        int p2 = n-1;
        int index = nums1.length -1;
        int tail;
        while(p2 >= 0) {

            if (p1 < 0) {
                tail = nums2[p2];
                p2--;
            } else {
                if (nums2[p2] >= nums1[p1]) {
                    tail = nums2[p2];
                    p2--;
                } else {
                    tail = nums1[p1];
                    p1--;
                }
            }

            nums1[index] = tail;
            System.out.println("tail=" + tail);
            index--;
        }
    }
}
