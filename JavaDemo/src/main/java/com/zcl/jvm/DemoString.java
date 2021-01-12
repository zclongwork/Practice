package com.zcl.jvm;

public class DemoString {

    public static void main(String[] args) {


        String a = new String("abc");
        System.out.println("a.intern() =  " + a.intern());
        String b = "abc";



        System.out.println(a==b);


//        String s0="kvill";
//        String s1=new String("kvill");
//        String s2="kv" + new String("ill");
//        System.out.println( s0==s1 );
//        System.out.println( s0==s2 );
//        System.out.println( s1==s2 );

    }
}
