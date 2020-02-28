package com.zcl.practice.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zcl.practice.R;

public class FragmentDemo2 extends Fragment {
    private static final String TAG = "FragmentDemo2";
    private View mRootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "1 onStart");
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
