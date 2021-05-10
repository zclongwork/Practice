package com.zcl.practice.rx;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.zcl.practice.BaseActivity;
import com.zcl.practice.R;
import com.zcl.practice.util.FileUtils;
import com.zcl.practice.util.Utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Description Rxjava使用
 * Author ZhangChenglong
 * Date 17/12/14 14:52
 */

public class RxJavaDemoActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "RxJavaDemoActivity";
    private RecyclerView mRecycleView;
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        setContentView(R.layout.activity_rx_demo);
        mRecycleView = (RecyclerView) findViewById(R.id.recycler_view);
        findViewById(R.id.bt_test).setOnClickListener(this);
        findViewById(R.id.bt_search).setOnClickListener(this);
        findViewById(R.id.bt_show).setOnClickListener(this);
        findViewById(R.id.bt_play).setOnClickListener(this);
        
        
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.bt_test:
//            RxJavaTest.test();
            RxJavaTest.test2();
            break;
        case R.id.bt_search:
            scanMp4File();
//            scanFileAsync(RxJavaDemoActivity.this, "/sdcard/Android/data/com.ss.android.article.news/cache/hashedvideos/");
            break;
        case R.id.bt_show:
            chainCalls();
            break;
        case R.id.bt_play:
//            playVideo("/sdcard/Android/data/com.smile.gifmaker/cache/.video_cache/1fa9aaf8957d4481d15c2ab9997a9ecb.mp4");
            playVideo("/sdcard/Android/data/com.smile.gifmaker/cache/.video_cache/b1a23bc65f04d644be8df60cc033599b.mp4");
                break;
        }
    }
    
    public void scanFileAsync(Context ctx, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        ctx.sendBroadcast(scanIntent);
    }
    
    private void refreshView(List<VideoInfo> data) {
        
        VideoAdapter adapter = new VideoAdapter(this, data);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(adapter);
    }
    
    void test() {
        //创建一个上游 Observable：
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        
        
        //创建一个下游 Observer
        Observer<Integer> observer = new Observer<Integer>() {
               @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "subscribe");
            }
        
            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "" + value);
            }
        
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "error");
            }
        
            @Override
            public void onComplete() {
                Log.d(TAG, "complete");
            }
        };
        //建立连接
        observable.subscribe(observer);
        
    }
    
    /**
     * 链式调用
     */
    void chainCalls() {
        Observable.create(new ObservableOnSubscribe<List<VideoInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<VideoInfo>> emitter) throws Exception {
                final long time = System.currentTimeMillis();
    
//                List<VideoInfo> list = getVideoList();
                List<VideoInfo> list = getSimpleVideoList();
    
                Log.e(TAG, " time: " + (System.currentTimeMillis() - time));
                
                emitter.onNext(list);
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VideoInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "链式调用 subscribe");
                    }
                
                    @Override
                    public void onNext(List<VideoInfo> value) {
                        Log.d(TAG, "" + value);
                        refreshView(value);
                    }
                
                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "error");
                    
                    }
                
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "complete");
                    }
                });
    }
    
    void convert() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
        
            }
        });

    }
    
    String getFilePath() {
        return "https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/legend.png";
    }
    
    private void scanMp4File() {
        Observable.create(new ObservableOnSubscribe<List<VideoInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<VideoInfo>> emitter) throws Exception {
//                List<VideoInfo> list = getVideoList();
//                emitter.onNext(list);
                Log.e(TAG, " kaishi ");
                getAllFile();
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VideoInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "链式调用 subscribe");
                    }
                
                    @Override
                    public void onNext(List<VideoInfo> value) {
                        Log.d(TAG, "" + value);
//                        refreshView(value);
                    }
                
                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "error");
                    
                    }
                
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "complete");
                    }
                });
        
    }
    
    private void playVideo(String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
//        String path = Environment.getExternalStorageDirectory().getPath()+ "/1.mp4";//该路径可以自定义
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "video/*");
        startActivity(intent);
    
    
        Log.d(TAG, "getCacheDir: " + getCacheDir() + " || getExternalCacheDir : " + getExternalCacheDir());
    }

    
    private void getAllFile() {
        String dir = "/sdcard/";
//        String dir = "/sdcard/Android/data/com.ss.android.article.news/cache/hashedvideos/";
        
        final long time = System.currentTimeMillis();
        
        final List<String> fileList = new ArrayList<>();
        FileUtils.findFiles(dir, ".mp4", fileList);
    
        Log.e(TAG," size : " + fileList.size() + " time: " + (System.currentTimeMillis() - time));
        fileList.add("/sdcard/Android/data/com.smile.gifmaker/cache/.video_cache/b1a23bc65f04d644be8df60cc033599b.mp4");
        String[] mimeTypes = {"video/mp4"};
        MediaScannerConnection.scanFile(this,
                fileList.toArray(new String[fileList.size()]),
                mimeTypes,
                new MediaScannerConnection.OnScanCompletedListener() {
                    private int count;
                
                    @Override
                    public void onScanCompleted(String s, Uri uri) {
                        count++;
                        Log.e(TAG, "onScanCompleted:::" + s + " ||| uri: " + uri);
                        if (count == fileList.size()) {
                            Log.e(TAG, "time 2:" + (System.currentTimeMillis() - time));
                        }
                    }
                });

    }
    
    
    
    
    private List<VideoInfo> getVideoList() {
        List<VideoInfo> sysVideoList = new ArrayList<>();
        // MediaStore.Video.Thumbnails.DATA:视频缩略图的文件路径
        String[] thumbColumns = { MediaStore.Video.Thumbnails.DATA,
                MediaStore.Video.Thumbnails.VIDEO_ID };
        
        // MediaStore.Video.Media.DATA：视频文件路径；
        // MediaStore.Video.Media.DISPLAY_NAME : 视频文件名，如 testVideo.mp4
        // MediaStore.Video.Media.TITLE: 视频标题 : testVideo
        String[] mediaColumns = { MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.MIME_TYPE,
                MediaStore.Video.Media.DISPLAY_NAME };
        
        Cursor cursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                mediaColumns, null, null, null);
        
        if(cursor != null) {
            int i = 0;
            while (cursor.moveToNext()) {
                Log.d(TAG, "while scan :::" + i);
                i++;
                VideoInfo info = new VideoInfo();
                int id = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.Video.Media._ID));
                Cursor thumbCursor = managedQuery(
                        MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                        thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID
                                + "=" + id, null, null);
                if (thumbCursor.moveToFirst()) {
                    info.setThumbPath(thumbCursor.getString(thumbCursor
                            .getColumnIndex(MediaStore.Video.Thumbnails.DATA)));
                }
                info.setPath(cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));
                info.setTitle(cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)));
                
                info.setDisplayName(cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)));
                info.setMimeType(cursor
                        .getString(cursor
                                .getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)));
                
                sysVideoList.add(info);
            }
            Utility.close(cursor);
        }
        return sysVideoList;
    }

    
    private List<VideoInfo> getSimpleVideoList()  {
        List<VideoInfo> sysVideoList = new ArrayList<>();
        VideoInfo info = new VideoInfo();
        info.setPath("/sdcard/Android/data/com.smile.gifmaker/cache/.video_cache/b1a23bc65f04d644be8df60cc033599b.mp4");
        sysVideoList.add(info);
        return sysVideoList;
    }
}
