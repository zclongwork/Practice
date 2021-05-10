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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_zoom);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        mRecyclerView.addOnItemTouchListener(new ZoomScale());
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
        mRecyclerView.setLayoutManager(new ZoomLayoutManager(this));
//        mRecyclerView.setLayoutManager(new TopHeavyLayoutManager());
//        TopSnapHelper helper = new TopSnapHelper(2);
//        helper.attachToRecyclerView(mRecyclerView);
    }



    static class ZoomScale implements RecyclerView.OnItemTouchListener, GestureDetector.OnGestureListener {
        RecyclerView recView;

        GestureDetector detector;

        @Override //这个方法用于判断点触手势要不要被拦截，要拦截的话就会把手势送给下面的onTouchEvent去处理。
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            recView = rv;//获取外面传过来的要进行缩放的RecyclerView

            detector = new GestureDetector(rv.getContext(), this);

            //缩放的手势起码要两根手指来完成吧？所以检测到RecyclerView上的触点大于1的时候，return true就是把这类手势转送给onTouchEvent方法去处理。
            if (e.getPointerCount() == 1) {
                return true;
            } else {//其他操作则不拦截，仍由RecyclerView自身去处理。
                return false;
            }
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            detector.onTouchEvent(e);
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            Log.d(TAG, "onScrolled dx="+ distanceX + " dy=" + distanceY);
            if (Math.abs(distanceY) > Math.abs(distanceX)) {
                RecyclerView.LayoutManager layoutManager = recView.getLayoutManager();
                int count = layoutManager.getItemCount();

                if (count > 1) {
                    Log.d(TAG, "onScroll count="+ count + " dy=" + distanceY);
                    View view = layoutManager.getChildAt(0);
                    view.setScaleX(1.3f);

                    View view1 = layoutManager.getChildAt(1);
                    view1.setScaleX(0.7f);
                }




            }


            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    public void click(View view) {

    }
}
