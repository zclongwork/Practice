package com.zcl.practice.ioc;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) //将来可能写在其他注解上
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    String listenerSetter();
    Class<?> listenerType();

    String callbackMethod();



//    button2.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//        }
//    });
}
