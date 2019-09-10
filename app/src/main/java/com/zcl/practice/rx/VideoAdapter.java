package com.zcl.practice.rx;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zcl.practice.R;

import java.util.List;

/**
 * Description TODO
 * Author ZhangChenglong
 * Date 17/12/21 11:16
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    
    private static final String TAG = "VideoAdapter";
    private Context context;
    private List<VideoInfo> mData;
    
    public VideoAdapter(Context context, List<VideoInfo> data) {
        this.context = context;
        this.mData = data;
    }
    
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_video, parent, false));
        return holder;
    }
    
  
    
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (mData == null) {
            return;
        }
        VideoInfo info = mData.get(position);
        holder.tv.setText("path: " + info.getPath());
    
//        if (!TextUtils.isEmpty(info.getThumbPath())) {
//            Log.d(TAG, "getThumbPath : " + info.getThumbPath());
//            holder.image.setImageURI(Uri.parse("file://" +info.getThumbPath()));
//        }
    
        if (!TextUtils.isEmpty(info.getPath())) {
            Log.d(TAG, "getPath : " + "file://" +info.getPath());
            holder.image.setImageURI(Uri.parse("file://" +info.getPath()));
        }
    }
    
    @Override
    public int getItemCount() {
        return mData.size();
    }
    
    class MyViewHolder extends RecyclerView.ViewHolder {
        
        TextView tv;
        SimpleDraweeView image;
        
        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv);
            image = (SimpleDraweeView) view.findViewById(R.id.image);
        }
    }
}
