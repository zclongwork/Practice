package com.zcl.library.net;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.TimeUnit;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpManager {

    private static final String TAG = "OkHttpManager";

    //OkHttpClient用途分类(Fresco、OkHttp、Retrofit)
    public static final int TYPE_FRESCO = 1;
    public static final int TYPE_NETWORK = 2;
    public static final int TYPE_RETROFIT = 3;

    private OkHttpClient mRetrofitClient;
    private OkHttpClient mLongTimeoutRetrofitClient;
    private OkHttpClient mRoomClient;

    /**
     * 连接超时时间
     */
    private static final int CONNECT_TIMEOUT = 15000;
    /**
     * 读取超时时间
     */
    private static final int READ_TIMEOUT = 15000;
    /**
     * 写入超时时间
     */
    private final static int WRITE_TIMEOUT = READ_TIMEOUT;


    //记录log相关
    private HttpLoggingInterceptor loggingInterceptor;


    @IntDef({TYPE_FRESCO, TYPE_NETWORK, TYPE_RETROFIT})
    @Retention(RetentionPolicy.SOURCE)
    @interface DirectType {
    }


    private OkHttpManager() {

    }

    public static OkHttpManager getInstance() {
        return OkHttpManagerHolder.INSTANCE;
    }

    public OkHttpClient getOkHttpClient(@DirectType int type) {
        return getRetrofitClientWithoutProxy();
    }

    public OkHttpClient getShortTimeoutOkHttpClient() {
        return getShortTimeoutRetrofitClientWithoutProxy();
    }

    public OkHttpClient getRoomOkHttpClient() {
        if (loggingInterceptor == null) {
            loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(@NonNull String message) {
                    //打印retrofit日志
                    LogUtils.d(TAG, "retrofitBack = " + message);
                }
            });
            if (LogUtils.isOpen()) {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            }
        }
        if (mRoomClient == null) {
            OkHttpClient mClient = new OkHttpClient.Builder()
                    .build();

            mRoomClient = mClient.newBuilder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
                            Request original = chain.request();
                            Request request = original.newBuilder()
                                    //todo UA设置
//                                    .header("User-Agent", SocketUtil.encryptContent(AppInfoUtils.getAppInfo()))
                                    .method(original.method(), original.body())
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .addInterceptor(loggingInterceptor)
                    .build();
        }
        return mRoomClient;

    }


    private OkHttpClient getShortTimeoutRetrofitClientWithoutProxy() {
        if (loggingInterceptor == null) {
            loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(@NonNull String message) {
                    //打印retrofit日志
                    LogUtils.d(TAG, "retrofitBack = " + message);
                }
            });
            if (LogUtils.isOpen()) {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

            }
        }
        if (mLongTimeoutRetrofitClient == null) {

            OkHttpClient mClient = new OkHttpClient.Builder()
//                    .connectTimeout(CONNECT_TIMEOUT / 3, TimeUnit.MILLISECONDS)
//                    .writeTimeout(WRITE_TIMEOUT / 3, TimeUnit.MILLISECONDS)
//                    .readTimeout(READ_TIMEOUT / 3, TimeUnit.MILLISECONDS)
                    .build();

            mLongTimeoutRetrofitClient = mClient.newBuilder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
                            Request original = chain.request();
                            Request request = original.newBuilder()
//                                    .header("User-Agent", SocketUtil.encryptContent(AppInfoUtils.getAppInfo()))
                                    .method(original.method(), original.body())
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .addInterceptor(loggingInterceptor)
                    .build();
        }
        return mLongTimeoutRetrofitClient;
    }

    private OkHttpClient getRetrofitClientWithoutProxy() {
        if (loggingInterceptor == null) {
            loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(@NonNull String message) {
                    //打印retrofit日志
                    LogUtils.d(TAG, "retrofitBack = " + message);
                }
            });
            if (LogUtils.isOpen()) {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

            }
        }
        if (mRetrofitClient == null) {

            OkHttpClient mClient = new OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                    .build();

            mRetrofitClient = mClient.newBuilder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
                            Request original = chain.request();
                            Request request = original.newBuilder()
//                                    .header("User-Agent", SocketUtil.encryptContent(AppInfoUtils.getAppInfo()))
                                    .method(original.method(), original.body())
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .addInterceptor(loggingInterceptor)
                    .build();
        }
        return mRetrofitClient;
    }

    private static class OkHttpManagerHolder {
        private static final OkHttpManager INSTANCE = new OkHttpManager();
    }
}
