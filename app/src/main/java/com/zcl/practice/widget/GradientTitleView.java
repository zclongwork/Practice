package com.zcl.practice.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

public class GradientTitleView extends SimplePagerTitleView {


    public GradientTitleView(Context context) {
        super(context);
        // single 或者 setGravity 会引起shader 效果不对
        setSingleLine(false);
        setMaxLines(1);

        this.getPaint().setShader(null);
    }

    @Override
    public void onSelected(int index, int totalCount) {

//setTextColor  设置为shader起始颜色，否则第一次显示效果不正常
        setTextColor(Color.RED);

        float x0 = 0;
        float x1 = getWidth();

        LinearGradient mShader = new LinearGradient(0, 0,
                getPaint().getTextSize()* getText().length(), 0,
                Color.RED, Color.BLUE, Shader.TileMode.REPEAT);
        Log.i("GradientTitleView", getText() + " getShader:" + getPaint().getShader());
        this.getPaint().setShader(mShader);
        this.invalidate();
//
//        Log.d("GradientTitleView", index + " left:" + getContentLeft() + " right:" + getContentRight() + " w1:" + getMeasuredWidth());

//        Log.e("GradientTitleView", index + " x0:" + x0 + " x1:" + x1 + " w:" + (x1-x0));
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        setTextColor(mNormalColor);
        this.getPaint().setShader(null);
        this.invalidate();
    }

}
