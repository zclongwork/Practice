package com.zcl.practice.ioc;

import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InjectUtil {

    public static void inject(Object context) {

        injectLayout(context);

        injectView(context);

        injectClick(context);

    }

    //处理多种类型事件
    private static void injectClick(Object context) {
        Class<?> clazz = context.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method:methods) {
            //获取方法的注解
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<?> annotationClass = annotation.annotationType();
                EventBase eventBase = annotationClass.getAnnotation(EventBase.class);
                if (eventBase == null) {
                    continue;
                }

                String listerSetter = eventBase.listenerSetter();
                String callbackMethod = eventBase.callbackMethod();
                Class<?> listenerType = eventBase.listenerType();

                Method valueMethod;

                try {

                    valueMethod = annotationClass.getDeclaredMethod("value");
                    int[] viewIds = (int[]) valueMethod.invoke(annotation);
                    for (int viewId:viewIds ) {
                        Method findViewByIdmethod = clazz.getMethod("findViewById", int.class);
                        View view = (View) findViewByIdmethod.invoke(context, viewId);
                        if (view == null) {
                            continue;
                        }

                        ListenerInvocationHandler handler = new ListenerInvocationHandler(context, method);

                        Object proxy = Proxy.newProxyInstance(context.getClass().getClassLoader(), new Class[]{listenerType},handler);

                        Method viewClick = view.getClass().getMethod(listerSetter, listenerType);
                        viewClick.invoke(view, proxy);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        }

    }

    private static void injectView(Object context) {
        Class<?> clazz = context.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                ViewInject viewInject = field.getAnnotation(ViewInject.class);
                if (viewInject != null) {
                    try {
                        //findViewById
                        int id = viewInject.value();
                        Method method = clazz.getMethod("findViewById", int.class);
                        View view = (View) method.invoke(context, id);

                        //字段赋值
                        field.setAccessible(true);
                        field.set(context, view);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private static void injectLayout(Object context) {
        int layoutId = 0;
        Class<?> clazz = context.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            layoutId = contentView.value();
            try {
                Method method = clazz.getMethod("setContentView", int.class);
                method.invoke(context, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
