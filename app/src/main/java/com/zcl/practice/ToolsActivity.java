package com.zcl.practice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;

import com.zcl.practice.plugin.PluginDemoActivity;
import com.zcl.practice.proxy.Subject;
import com.zcl.practice.proxy.SubjectImpl;
import com.zcl.practice.proxy.SubjectProxy;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;

import java.net.URISyntaxException;
import java.net.URL;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;


/**
 * Description
 * Author ZhangChenglong
 * Date 21/1/26 17:18
 */

public class ToolsActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "ToolsActivity";

    private Context mContext;
    Handler uiHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
        mContext = this;
        findViewById(R.id.notify_pic).setOnClickListener(this);
        findViewById(R.id.notify_pic_big).setOnClickListener(this);
        findViewById(R.id.proxy).setOnClickListener(this);
    }

//    Bitmap bitmap = getBitmapFromURL("https://vi3.xiu123.cn/live/2020/11/23/14/1010v1606114240760959616_b.webp");



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.notify_status:
                status();
                break;
            case R.id.notify_pic:
                def();
                break;
            case R.id.notify_pic_big:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = getBitmapFromURL("https://vi3.xiu123.cn/live/2020/11/23/14/1010v1606114240760959616_b.webp");
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "bitmap:" + bitmap);
//                                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, NotificationChannel.DEFAULT_CHANNEL_ID);
                                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, "default");
                                showBigNotification(bitmap, mBuilder, "title", "message");
                            }
                        });
                    }
                }).start();
                break;
            case R.id.proxy:
                testProxy();
                break;
            default:
                break;
        }
    }

    private void status() {
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.areNotificationsEnabled();


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 23) {
//            StatusBarNotification[] notifications = notificationManager.getActiveNotifications();
        }

    }


    private void testProxy() {
        Subject subject = new SubjectImpl();
        InvocationHandler subjectProxy = new SubjectProxy(subject);

        //动态代理第2个参数：被代理类的接口，如果有多个就传数组； 第3个参数：代理类实例
        Subject proxyInstance = (Subject) Proxy.newProxyInstance(subjectProxy.getClass().getClassLoader(), subject.getClass().getInterfaces(), subjectProxy);
        proxyInstance.hello("world");
    }

    private void def() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(ToolsActivity.this, PluginDemoActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(ToolsActivity.this, 0,intent,0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "default";
            String channelName = "默认通知";
            //new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH));
        }
        Notification notification = new NotificationCompat.Builder(ToolsActivity.this, "default")
                .setContentTitle("title")
                .setContentText("text")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
//                .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/luna.ogg")))
                .setVibrate(new long[]{0,1000,1000,1000})
                .setLights(Color.GREEN, 1000, 1000)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(1, notification);
    }

    private void showBigNotification(Bitmap bitmap, NotificationCompat.Builder mBuilder,String title, String  message) {

        mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker(title).setWhen(0);
        Intent intent = new Intent().setClassName("com.zcl.practice", "com.zcl.practice.plugin.PluginDemoActivity"); // give any activity name which you want to open

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));
        mBuilder.setContentTitle(title);
        mBuilder.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        mBuilder.setContentText(message);

        mBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));/*Notification with Image*/

        mBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));/*Notification with Image*/




        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        if (Build.VERSION.SDK_INT >= 21) mBuilder.setVibrate(new long[0]);
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
//        notificationManager.notify(Config.NOTIFICATION_ID, mBuilder.build());
        notificationManager.notify(1, mBuilder.build());
    }

    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }









}
