package com.zcl.practice.plugin;

import android.content.Intent;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HookUtil {
    private static String TAG = "HookUtil";

    private static final String TARGET_INTENT = "target_intent";

    public static void hookAms() {

        try {
            Class<?>  clazz = Class.forName("android.app.ActivityManager");
            Field singletonFiled = clazz.getDeclaredField("IActivityManagerSingleton");
            singletonFiled.setAccessible(true);
            Object singleton = singletonFiled.get(null);

            // 获取 系统的 IActivityManager 对象
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            final Object mInstance = mInstanceField.get(singleton);

            Class<?> iActivityManagerClass = Class.forName("android.app.IActivityManager");



            Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[]{iActivityManagerClass}, new InvocationHandler(){

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    // do something
                    // Intent的修改 -- 过滤
                    /**
                     * IActivityManager类的方法
                     * startActivity(whoThread, who.getBasePackageName(), intent,
                     *                         intent.resolveTypeIfNeeded(who.getContentResolver()),
                     *                         token, target != null ? target.mEmbeddedID : null,
                     *                         requestCode, 0, null, options)
                     */
                    // 过滤
                    if ("startActivity".equals(method.getName())) {
                        int index = -1;

                        for (int i = 0; i < args.length; i++) {
                            if (args[i] instanceof Intent) {
                                index = i;
                                break;
                            }
                        }
                        // 启动插件的
                        Intent intent = (Intent) args[index];

                        Intent proxyIntent = new Intent();
                        proxyIntent.setClassName("com.zcl.practice",
                                "com.zcl.practice.plugin.ProxyActivity");

                        proxyIntent.putExtra(TARGET_INTENT, intent);

                        args[index] = proxyIntent;
                    }

                    // args  method需要的参数  --- 不改变原有的执行流程
                    // mInstance 系统的 IActivityManager 对象
                    return method.invoke(mInstance, args);
                }
            });

            // ActivityManager.getService() 替换成 proxyInstance
            mInstanceField.set(singleton, proxyInstance);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
