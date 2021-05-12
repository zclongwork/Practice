package com.zcl.practice.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zcl.practice.R;
import com.zcl.practice.layoutManager.StackLayoutManager;
import com.zcl.practice.layoutManager.TopHeavyLayoutManager;
import com.zcl.practice.layoutManager.TopSnapHelper;
import com.zcl.practice.layoutManager.ZoomLayoutManager;

public class PageZoomActivity extends FragmentActivity {

    private static final String TAG = "PageZoomActivity";
    private RecyclerView mRecyclerView;
    private ZoomAdapter mAdapter;
    ZoomLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_zoom);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new ZoomAdapter();
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                int count = mAdapter.getItemCount();

            }
        });
        mRecyclerView.setAdapter(mAdapter);


//        mRecyclerView.setLayoutManager(new StackLayoutManager(this));

        layoutManager = new ZoomLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setLayoutManager(new TopHeavyLayoutManager());
//        TopSnapHelper helper = new TopSnapHelper(2);
//        helper.attachToRecyclerView(mRecyclerView);
    }

    public void click(View view) {
        layoutManager.rollBack();
        mRecyclerView.scheduleLayoutAnimation();
        mAdapter.notifyDataSetChanged();

    }
}
