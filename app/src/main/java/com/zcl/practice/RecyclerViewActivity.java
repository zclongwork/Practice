package com.zcl.practice;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Description TODO
 * Author ZhangChenglong
 * Date 18/7/13 15:43
 */

public class RecyclerViewActivity extends AppCompatActivity {
    
    private static final String TAG = "AnimationActivity";
    
    private RecyclerView mRecyclerView;
    RecyclerViewActivity.HomeAdapter adapter;
    
    private List<String> mDatas = new ArrayList<>();
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        
        for(int i =0 ;i< 50;i++) {
            mDatas.add("text " + i);
        }
        
        
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                
                Log.d("TAGTAG", "position: " + position + " type:" + adapter.getItemViewType(position));
                if (position ==0) {
                    return 2;
                }
                return 1;
            }
        });
        mRecyclerView.setLayoutManager(manager);
        adapter = new RecyclerViewActivity.HomeAdapter();
        mRecyclerView.setAdapter(adapter);
        
        
        
    }
    
    
    class HomeAdapter extends RecyclerView.Adapter<RecyclerViewActivity.HomeAdapter.MyViewHolder> {
        
        
        @Override
        public RecyclerViewActivity.HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerViewActivity.HomeAdapter.MyViewHolder holder = new RecyclerViewActivity.HomeAdapter.MyViewHolder(
                    LayoutInflater.from(
                            RecyclerViewActivity.this).inflate(R.layout.item_home, parent,
                    false));
            return holder;
        }
        
        @Override
        public void onBindViewHolder(RecyclerViewActivity.HomeAdapter.MyViewHolder holder, int position) {
            holder.tv.setText(mDatas.get(position));
        }
        
        @Override
        public int getItemCount() {
            return mDatas.size();
        }
        
        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }
        
        class MyViewHolder extends RecyclerView.ViewHolder {
            
            TextView tv;
            
            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.tv);
            }
        }
    }
}
