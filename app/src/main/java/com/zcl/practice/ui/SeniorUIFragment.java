package com.zcl.practice.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zcl.practice.BaseFragment;
import com.zcl.practice.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 高级UI
 * Author zcl
 * Date 2021/9/22
 */
public class SeniorUIFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_senior_ui_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
