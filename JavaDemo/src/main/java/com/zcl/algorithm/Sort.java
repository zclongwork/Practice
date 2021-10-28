package com.zcl.algorithm;

/**
 * 1.冒泡排序
 * 2.快速排序
 * 3.选择排序
 */
public class Sort {

    public static void main(String[] args) {
        int[] arr = {52,63,14,59,68,35,8,67,45,99};

        //1 冒泡排序
//        bubble2(arr);

        selectSort(arr);

        PrintUtil.arrPrint(arr);
    }


    /**
     * 1.冒泡排序：
     *
     * 冒泡排序算法的运作如下：
     *
     * 1.比较相邻的元素。如果第一个比第二个大，就交换他们两个。
     * 2.对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。这步做完后，最后的元素会是最大的数。
     * 3.针对所有的元素重复以上的步骤，除了最后一个。
     * 4.持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
     *
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

    public static int[] bubble2(int[] arr) {
        for (int i = 1; i < arr.length; i++) {

            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }

        return arr;
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

            for (int j = i + 1; j < arr.length; j++) { //走訪未排序的元素
                if (arr[j] < arr[min]) {//找到目前最小值
                    min = j;//记录最小值
                }
            }

            if (min != i) {
                swap(arr, i, min);//交换
            }
        }
    }

    private static void swap(int[] arr, int indexI, int indexJ) {
        int temp = arr[indexI];
        arr[indexI] = arr[indexJ];
        arr[indexJ] = temp;
    }



}
