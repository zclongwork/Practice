package com.zcl.algorithm;

import java.util.ArrayList;

public class CharSort {


//
//    public static void main(String[] args) {
//
//        char[] chars = {'c','b', 'a', 'd'};
//        for (int i=0;i<chars.length; i++) {
//
//
//
//        }
//    }



    public static void main(String[] a) {
        int[] nums = {1,1,2};
        removeDuplicates(nums);
    }

    public static int removeDuplicates(int[] nums) {
        int newlength = 0;
        if(nums != null){
            int j = 0;
            for(int i=1;i<nums.length;i++) {
                if (nums[i] != nums[j]) {
                    j++;
                    nums[j]=nums[i];
                    newlength = j;
                }
            }


            for(int i=0;i<nums.length;i++) {
                System.out.println(newlength +"i:" + i + " : " + nums[i]);
            }

        }
        return newlength;
    }

}
