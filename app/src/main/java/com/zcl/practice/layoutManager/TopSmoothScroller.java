package com.zcl.practice.layoutManager;

import android.content.Context;

import androidx.recyclerview.widget.LinearSmoothScroller;

public class TopSmoothScroller extends LinearSmoothScroller {

    TopSmoothScroller(Context context) {
        super(context);
    }

    @Override
    protected int getHorizontalSnapPreference() {
        return SNAP_TO_START;
    }

    @Override
    protected int getVerticalSnapPreference() {
        return SNAP_TO_START;
    }
}