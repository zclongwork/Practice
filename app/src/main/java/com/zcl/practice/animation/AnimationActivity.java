package com.zcl.practice.animation;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zcl.practice.R;

import java.util.ArrayList;
import java.util.List;

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
    
//        List<VideoInfo> lists = getVideoList();
//        for (VideoInfo info : lists) {
//            Log.e(TAG, info.getPath() + " | " + info.getDisplayName() + " | " + info.getMimeType());
//        }
        
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
            while (cursor.moveToNext()) {
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
        }
        return sysVideoList;
    }
    
    private static class VideoInfo {
        private String thumbPath;
        private String path;
        private String title;
        private String mimeType;
        private String displayName;
    
        public String getThumbPath() {
            return thumbPath;
        }
    
        public void setThumbPath(String thumbPath) {
            this.thumbPath = thumbPath;
        }
    
        public String getPath() {
            return path;
        }
    
        public void setPath(String path) {
            this.path = path;
        }
    
        public String getTitle() {
            return title;
        }
    
        public void setTitle(String title) {
            this.title = title;
        }
    
        public String getMimeType() {
            return mimeType;
        }
    
        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }
    
        public String getDisplayName() {
            return displayName;
        }
    
        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
    }
}
