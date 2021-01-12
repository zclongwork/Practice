package com.zcl.concurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * volatile保证可见性，但java运算并不是原子操作
 * 因此以下程序的运行结果不是20000，
 * 此例中保证count++的原子性后即可是程序输出预期的20000
 *
 * Author zcl
 * Date 2020-01-08
 */
public class VolatileTest {

    private static volatile int count = 0;
    private static int THREAD_COUNT = 20;

    public static void increase() {
        count++;
    }

    public static void main2(String[] args) {
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        increase();
                    }
                }
            });
            threads[i].setName("Thread_" + i);
            threads[i].start();
        }

        //等待所有累加线程都结束 studio运行除了main 线程外还有一个Monitor Ctrl-Break线程 因此这里是2
        while (Thread.activeCount()>2) {

            ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
            int noThreads = currentGroup.activeCount();
            Thread[] lstThreads = new Thread[noThreads];
            currentGroup.enumerate(lstThreads);
            for (int i = 0; i < noThreads; i++) {
                System.out.println("线程号：" + i + " = " + lstThreads[i].getName());
            }

            Thread.yield();
        }

        System.out.println("count:" + count);
    }

    public static void main(String[] args) {
        int[] arr ={1,2,2,1,1,3};
        boolean result = uniqueOccurrences(arr);
        System.out.println(result);
    }

    public static boolean uniqueOccurrences(int[] arr) {
        //1.hashmap key=数字 value=数字个数
        Map<Integer, Integer> map = new HashMap<>();
        for(Integer i :arr) {
            map.put(i, map.getOrDefault(i, 0)+1);
        }


        HashSet<Integer> set = new HashSet();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int value = entry.getValue();
            if (set.contains(value)) {
                return false;
            }
            set.add(value);
        }
        return true;
    }

    public int[] intersect(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        for(Integer i :nums1) {
            map.put(i, map.getOrDefault(i, 0)+1);
        }

        List<Integer> list = new ArrayList<>();
        for (Integer i : nums2) {
            if (map.getOrDefault(i, 0) > 0) {
                map.put(i, map.getOrDefault(i, 0) - 1);
                list.add(i);
            }
        }

        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }


}
