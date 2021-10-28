package com.zcl.practice.rx;


import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxJavaTest {
    private static final String TAG = "RxJavaTest";
    
    public static void test() {
        Observable.create(
                //1.这里是自定义数据源头
                new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                //4.建立订阅关系后 ObservableCreate的subscribeActual() source.subscribe(parent);
                emitter.onNext("hello");
            }
        })
             //2. 链式调用 上边返回ObservableCreate 这里实质是ObservableCreate.subscribe
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                //3.建立订阅关系后 ObservableCreate的subscribeActual() 执行observer.onSubscribe(parent);
                Log.d(TAG, "onSubscribe " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(@NonNull String o) {
                //5.发射器onNext 后这里执行
                Log.d(TAG, o  + " onNext " + Thread.currentThread().getName());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError" + Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete" + Thread.currentThread().getName());
            }
        });
        
    }



    public static void test2() {
        Observable.create(
                //1.这里是自定义数据源头
                new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                        Log.d(TAG, "subscribe " + Thread.currentThread().getName());
                        //4.建立订阅关系后 ObservableCreate的subscribeActual() source.subscribe(parent);
                        emitter.onNext("hello");
                    }
                })
//                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        //3.建立订阅关系后 ObservableCreate的subscribeActual() 执行observer.onSubscribe(parent);
                        Log.d(TAG, "onSubscribe " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(@NonNull String o) {
                        //5.发射器onNext 后这里执行
                        Log.d(TAG, o  + " onNext " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete" + Thread.currentThread().getName());
                    }
                });

    }

}
