package com.zcl.reflect;

import java.lang.reflect.Constructor;

public class TestConstructor {
    /*构造器相关*/
    public void testConstructor() throws Exception{
        String className = "com.zcl.reflect.Person";
        Class<Person> clazz = (Class<Person>) Class.forName(className);

        System.out.println("获取全部Constructor对象-----");
        Constructor<Person>[] constructors
                = (Constructor<Person>[]) clazz.getConstructors();
        for(Constructor<Person> constructor: constructors){
            System.out.println(constructor);
        }


        System.out.println("获取某一个Constructor 对象，需要参数列表----");
        Constructor<Person> constructor
                = clazz.getConstructor(String.class, int.class);
        System.out.println(constructor);

        //2. 调用构造器的 newInstance() 方法创建对象
        System.out.println("调用构造器的 newInstance() 方法创建对象-----");
        Person obj = constructor.newInstance("Mark", 18);
        System.out.println(obj.getName());
    }
}
