package com.zcl.practice.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieListener;
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
                    .inflate(R.layout.fragment_demo2, null);


            mRootView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    test();
                }
            }, 500);
        }
        return mRootView;
    }
    LottieAnimationView lottieAnimationView;
    private void test() {
        lottieAnimationView = mRootView.findViewById(R.id.lottie_gift_view);
        lottieAnimationView.useHardwareAcceleration(true);

        lottieAnimationView.setRepeatCount(1);
//        lottieAnimationView.setAnimation(tempLottie.getLottieJson());//在assets目录下的动画json文件名。 //"lottie/giftLove.json"
//        lottieAnimationView.setImageAssetsFolder(tempLottie.getLottieImages());//assets目录下的子目录，存放动画所需的图片 //"lottie/giftLove"

//        lottieAnimationView.setAnimation("lottie/love77/images");
//        lottieAnimationView.setImageAssetsFolder("lottie/love77/data.json");


        // 如果url不存在，app会直接崩溃，里面是异步的，可以通过LottieAnimationView.setFailureListener监听失败
        lottieAnimationView.setAnimationFromUrl("https://devpic.xiu123.cn/live/anigift/12862c7b9bcd3d.zip");

        lottieAnimationView.playAnimation();

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
