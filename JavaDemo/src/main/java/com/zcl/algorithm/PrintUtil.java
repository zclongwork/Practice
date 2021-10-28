package com.zcl.algorithm;

/**
 * TODO Description
 * Author zcl
 * Date 2021/10/28
 */
public class PrintUtil {

    /**
     * 打印数组
     * @param arr
     */
    public static void arrPrint(int[] arr) {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]);
            if (i != arr.length - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
        System.out.println(builder.toString());
    }
}
