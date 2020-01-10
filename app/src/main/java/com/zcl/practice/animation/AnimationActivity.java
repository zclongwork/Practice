package com.zcl.practice.animation;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zcl.practice.R;
import com.zcl.practice.ext.DownloadData;
import com.zcl.practice.ext.DownloadHelper;
import com.zcl.practice.ext.DownloadListener;

import audiovideo.zcl.com.fileloader.DownInfo;
import audiovideo.zcl.com.fileloader.DownloadJob;
import audiovideo.zcl.com.fileloader.FileLoaderTest;

/**
 * Description 动画
 * Author ZhangChenglong
 * Date 17/12/8 16:08
 */

public class AnimationActivity extends AppCompatActivity {
    
    private static final String TAG = "AnimationActivity";
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        
      
        findViewById(R.id.charm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
        
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test1();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test2();
            }
        });
    }
    
    private boolean checkPermission() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }
        
        
        
        void test() {
        toast(" test() ");
    
        String url = "http://192.168.120.23:8080/apkdownload/AppA_Sixrooms_xiuchang_9200-xiuchang_360_6.3.5_20191101-145646_635_jiagu_aligned_signed_9200-xiuchang_360.apk";
    
        DownloadHelper helper = DownloadHelper.getInstance();
        long id = helper.addDownload("下载", "测试一下怎么样", url, "shiliu.apk");
        
        helper.registerObserver(id, new DownloadListener() {
            @Override
            public void onChanged(DownloadData data) {
                Log.d(TAG, "onChanged:" + data.getStatus() + " url:" + data.getUri());
            }
        });
        
    }
    
    FileLoaderTest loader;
    
    void test1() {
    
         loader = FileLoaderTest.getInstance();
        loader.configureJobManager(getApplicationContext());
        
//     loader.addJob(new DownloadJob(1));
    
        String url = "http://192.168.120.23:8080/apkdownload/AppA_Sixrooms_xiuchang_9200-xiuchang_360_6.3.5_20191101-145646_635_jiagu_aligned_signed_9200-xiuchang_360.apk";
    
        DownInfo info = new DownInfo();
        info.setUrl(url);
        info.setFilePath(this.getCacheDir().getAbsolutePath());
        info.setFileName("test.apk");
    
    
        DownloadJob job = new DownloadJob(info, 1);
        loader.addJob(job);
//        loader.addJob(new DownloadJob(info, 2));
//        loader.addJob(new DownloadJob(info, 3));
    }
    
    void test2() {
        if (loader != null) {
            loader.getJobStatus();
        } else {
            Log.d(TAG, "loader is null");
        }
        
    }
    
    
    
    void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
