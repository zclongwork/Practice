package com.zcl.algorithm;

import java.util.Stack;

//https://leetcode-cn.com/problems/hanota-lcci/
//汉诺塔 递归
public class Hanota {

    Stack<Integer> A;
    Stack<Integer> B;
    Stack<Integer> C;

    public static void main(String[] args) {
//        Hanota hanota = new Hanota();
//        hanota.test();




    }

    public static int testF(int n) {
        if (n<=0) {
            return 0;
        }
        int result = testF(n-1);
        int result2 = testF(n-2);
        return result + result2;
    }


    public void test() {
//        move(A.size(), A, B, C);

        A = new Stack<>();

//        A.push(5);
//        A.push(4);
        A.push(3);
        A.push(2);
        A.push(1);

        B = new Stack<>();
        C = new Stack<>();

//        System.out.println(String.format("A:%s; B:%s; C:%s", A.toString(), B.hashCode(), C.hashCode()));

        move(A.size(),A, B, C );

        while (!C.empty()) {
            System.out.println(C.pop());
        }
    }

    // A上元素移动到C
    private void move(int n, Stack<Integer> A, Stack<Integer> B, Stack<Integer> C) {
        if (n == 1) {
            move2(A,C);
            return;
        }
        //1. 把A的n-1 借助C移动到B
        move(n-1, A, C, B);
        //2.把A最底部移到C
        move2(A,C);
        //3. B 上的元素移动到C
        move(n-1, B, A, C);

    }

    private void move2(Stack<Integer> source, Stack<Integer> target) {
        if (source.size() > 0) {
//            System.out.println("-->移动内容：" + source.peek());
            target.push(source.pop());
            log();
        }
    }

    int count = 0;

    private void log() {

        StringBuffer buffer = new StringBuffer();

        buffer.append("A:").append(arr2String(A.toArray()));
        buffer.append(" B:").append(arr2String(B.toArray()));
        buffer.append(" C:").append(arr2String(C.toArray()));

        System.out.println(String.format("步骤:%s   | %s", (++count) ,buffer.toString()));
    }

    private String arr2String(Object[] arr) {
        StringBuilder builder = new StringBuilder("[");

        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]);
            if (i != arr.length-1) {
                builder.append(",");
            }
        }
        builder.append("]");

        return builder.toString();
    }
}
