package com.zcl.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Client {
    public static void main(String[] args) throws Exception {

//        proxy();

//        getConstructors();
//        getMethods();
//        test();
//        getMyInstance();

        dynamicProxy();
    }

    static void proxy() {
        //创建委托类
        Subject mRealSubject=new RealSubject();
        //创建代理类
        ProxySubject mProxy = new ProxySubject(mRealSubject);
        //由代理类去做具体的操作
        mProxy.doSomething();
    }


    static void dynamicProxy() {
        // 委托类
        Subject mRealSubject = new RealSubject();
        // 委托类classLoader
        ClassLoader mClassLoader = mRealSubject.getClass().getClassLoader();
        // 委托类对应的ProxyHandler
        DynamicProxyHandler mProxyHandler = new DynamicProxyHandler(mRealSubject);
        Class[] mClasses = new Class[]{Subject.class};
        // 代理类
        Subject proxySubject = (Subject) Proxy.newProxyInstance(mClassLoader, mClasses, mProxyHandler);
        // 代理类调用方法
        proxySubject.doSomething();
    }


    /**
     * 1. 动态生成类的字节码,并打印生成类的构造函数
     */
    public static void getConstructors(){

        //动态生成代理类
        //ClassLoader:  每一个Class就必须有一个类加载器加载进来的。既然需要JVM动态生成Java类，所以要为动态生成类的字节码指定类加载器
        //Class Interfaces: 动态生成的字节码实现了哪些接口
        Class clazzProxy1 = Proxy.getProxyClass(Collections.class.getClassLoader(), Collection.class);

        //获取这个代理类的构造方法
        Constructor[] constructors = clazzProxy1.getConstructors();

        System.out.println("---------------------begin Construstors-----------------");
        //遍历构造方法
        for (Constructor constructor: constructors) {
            //获取每个名称
            String name = constructor.getName();
            StringBuilder sb = new StringBuilder(name);
            sb.append("(");
            //获取每个构造方法的参数类型
            Class[] clazzTypes = constructor.getParameterTypes();
            for (Class clazzType : clazzTypes) {
                sb.append(clazzType.getName()).append(".");
            }
            if(clazzTypes != null && clazzTypes.length != 0){
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append(")");
            System.out.println(sb.toString());
        }
    }

    /**
     * 2. 动态生成类的字节码,并打印动态类的每个方法
     */
    public static void getMethods(){
        //动态生成代理类
        Class clazzProxy1 = Proxy.getProxyClass(Collection.class.getClassLoader(), Collection.class);

        //获取这个代理类的构造方法
        Method[] methods = clazzProxy1.getMethods();

        System.out.println("---------------------begin getMethods-----------------");
        //遍历构造方法
        for (Method method: methods) {
            //获取每个名称
            String name = method.getName();
            StringBuilder sb = new StringBuilder(name);
            sb.append("(");
            //获取每个构造方法的参数类型
            Class[] clazzTypes = method.getParameterTypes();
            for (Class clazzType : clazzTypes) {
                sb.append(clazzType.getName()).append(".");
            }
            if(clazzTypes != null && clazzTypes.length != 0){
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append(")");
            System.out.println(sb.toString());
        }
    }

    /**
     * 3. 创建动态类的实例对象及调用其方法
     */
    public static void test() throws Exception{

        //通过打印构造方法，得到的动态代理类有一个InvocationHandler参数
        Class clazzProxy1 = Proxy.getProxyClass(Collection.class.getClassLoader(), Collection.class);
        //获取Constructor类
        Constructor constructor = clazzProxy1.getConstructor(InvocationHandler.class);
        //传递InvocationHandler参数,手动实现InvocationHander接口
        //返回的结果是Collection接口的对象
        Collection proxy1 = (Collection) constructor.newInstance(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                return null;
            }
        });
        /**
         * 通过打印生成的对象发现结果为null 有两种种可能：
         * 第一种可能是对象为null
         * 第二种可能是对象的toString()方法为null
         */
        System.out.println(proxy1);
        //对象没有报空指针异常，所以对象的toString为null,可以得出结论，代理类对象的toString()方法被代理类重写了。
        System.out.println(proxy1.toString());
        //调用一个方法，运行成功，所以proxy1不为null
        proxy1.clear();

        //调用size方法出错，为什么出错呢？size方法是有返回值的。
        proxy1.size();
    }


    public static void getMyInstance(){
        //Proxy.newInstance方法直接创建出代理对象
        Collection proxy1 = (Collection) Proxy.newProxyInstance(
                Collection.class.getClassLoader(),
                new Class[]{Collection.class},
                new InvocationHandler() {
                    //方法外部指定目标
                    List target = new ArrayList<>();
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        //在调用代码之前加入系统功能代码
                        long startTime = System.currentTimeMillis();
                        //睡眠1秒钟
                        Thread.sleep(1000);
                        //目标方法
                        Object retVal = method.invoke(target, args);
                        //在调用代码之后加入系统功能代码
                        long endTime = System.currentTimeMillis();
                        System.out.println( method.getName() + "方法花费了:" + (endTime - startTime) + "毫秒");
                        return retVal;
                    }
                });

        proxy1.add("a");
        proxy1.add("b");
        proxy1.add("c");
        //3
        System.out.println(proxy1.size());
    }




}
