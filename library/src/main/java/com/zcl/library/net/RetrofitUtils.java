package com.zcl.library.net;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitUtils {
    public static final String TAG = RetrofitUtils.class.getSimpleName();
    private static Retrofit mDefaultretrofit;

    public enum RetrofitConverter {
        GSON,
        STRING
    }

    /**
     * 获取retrofit 实例
     *
     * @param converter 数据解析器
     * @param baseUrl   baseurl
     * @return mDefaultretrofit 实例
     */
    @Deprecated
    public static Retrofit getRetrofit(@NonNull RetrofitConverter converter, @NonNull String baseUrl) {
        Converter.Factory converterFactory;
        if (RetrofitConverter.GSON == converter) {
            converterFactory = CustomGsonConverterFactory.create();
        } else {
            converterFactory = StringConverterFactory.create();
        }
        return new Retrofit.Builder()
                .client(OkHttpManager.getInstance().getOkHttpClient(OkHttpManager.TYPE_RETROFIT))
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }


    /**
     * {@link #getAsyncRetrofit}
     *
     * @param baseUrl
     * @return
     */
    @Deprecated
    public static Retrofit getRetrofit(@NonNull String baseUrl) {
        return getRetrofit(RetrofitConverter.GSON, baseUrl);
    }

    /**
     * {@link RxSchedulersUtil#rxSchedulerToMain()}
     */
    public static Retrofit getAsyncRetrofit(@NonNull RetrofitConverter converter, @NonNull String baseUrl) {
        Converter.Factory converterFactory;
        if (RetrofitConverter.GSON == converter) {
            converterFactory = CustomGsonConverterFactory.create();
        } else {
            converterFactory = StringConverterFactory.create();
        }
        if (UrlStrs.URL_MOBILE.equals(baseUrl) && RetrofitConverter.GSON == converter) {
            return getMyDefaultRetrofit();
        }
        return new Retrofit.Builder()
                .client(OkHttpManager.getInstance().getOkHttpClient(OkHttpManager.TYPE_RETROFIT))
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * {@link RxSchedulersUtil#rxSchedulerToMain()}
     */
    public static Retrofit getShortTimeoutAsyncRetrofit(@NonNull RetrofitConverter converter, @NonNull String baseUrl) {
        Converter.Factory converterFactory;
        if (RetrofitConverter.GSON == converter) {
            converterFactory = CustomGsonConverterFactory.create();
        } else {
            converterFactory = StringConverterFactory.create();
        }
        if (UrlStrs.URL_MOBILE.equals(baseUrl) && RetrofitConverter.GSON == converter) {
            return getMyDefaultRetrofit();
        }
        return new Retrofit.Builder()
                .client(OkHttpManager.getInstance().getShortTimeoutOkHttpClient())
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .baseUrl(baseUrl)
                .build();
    }


    private static Retrofit getMyDefaultRetrofit() {
        if (null == mDefaultretrofit) {
            Converter.Factory converterFactory = CustomGsonConverterFactory.create();
            mDefaultretrofit = new Retrofit.Builder()
                    .client(OkHttpManager.getInstance().getOkHttpClient(OkHttpManager.TYPE_RETROFIT))
                    .addConverterFactory(converterFactory)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                    .baseUrl(UrlStrs.URL_MOBILE)
                    .build();
        }
        return mDefaultretrofit;
    }


    /*
     * @description:If you use createAsync() when creating the RxJava2CallAdapterFactory
     * it will create fully-async Observable instances that don't require a subscribeOn
     * and which use OkHttp's Dispatcher just like Retrofit would otherwise.
     * Then you only need observeOn to move back to the main thread,
     * and you avoid all additional thread creation.(by JakeWharton)
     * @params:
     * @return:
     * @author:yanqing
     * @date:18/7/11
     */

    /**
     * {@link RxSchedulersUtil#rxSchedulerToMain()}
     */
    public static Retrofit getAsyncRetrofit(@NonNull String baseUrl) {
        if (UrlStrs.URL_MOBILE.equals(baseUrl)) {
            return getMyDefaultRetrofit();
        }
        Converter.Factory converterFactory = CustomGsonConverterFactory.create();
        return new Retrofit.Builder()
                .client(OkHttpManager.getInstance().getOkHttpClient(OkHttpManager.TYPE_RETROFIT))
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .baseUrl(baseUrl)
                .build();
    }

    public static Retrofit getAsyncRetrofit(@NonNull String baseUrl, Converter.Factory converterFactory) {
        return new Retrofit.Builder()
                .client(OkHttpManager.getInstance().getOkHttpClient(OkHttpManager.TYPE_RETROFIT))
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .baseUrl(baseUrl)
                .build();
    }

}
