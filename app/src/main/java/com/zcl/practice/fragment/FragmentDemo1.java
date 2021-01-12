package com.zcl.practice.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zcl.practice.R;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

/**
 * viewpager FragmentStatePagerAdapter  第3个Fragment 切回第1个Fragment
 * 生命周期重新执行一遍 savedInstanceState不为空
 *
 * 为什么？
 *
 */
public class FragmentDemo1 extends Fragment {
    private static final String TAG = "FragmentDemo1";
    private View mRootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "1 onAttach");
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "2 onCreate savedInstanceState: " + savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "3 onCreateView savedInstanceState: " + savedInstanceState);
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                Log.w(TAG, "onCreateView removeView");
                parent.removeView(mRootView);
            }
        } else {
            mRootView = inflater
                    .inflate(R.layout.fragment_demo1, null);
            TextView tv = mRootView.findViewById(R.id.tv_1);

            tv.setGravity(Gravity.CENTER);
            int padding = UIUtil.dip2px(getContext(), 10);
            tv.setPadding(padding, 0, padding, 0);
//            tv.setSingleLine();
            tv.setMaxLines(1);
            tv.setEllipsize(TextUtils.TruncateAt.END);


            LinearGradient mShader = new LinearGradient(0, 0,
                    tv.getPaint().getTextSize()* tv.getText().length(), 0,
                    Color.RED, Color.BLUE, Shader.TileMode.CLAMP);
            tv.getPaint().setShader(mShader);
            tv.invalidate();
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "4 onActivityCreated savedInstanceState: " + savedInstanceState);
    }



    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "5 onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "6 onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "7 onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "8 onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "9 onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "10 onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "11 onDetach");
    }
}
