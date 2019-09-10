package com.zcl.practice.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import com.zcl.practice.R;

import static android.animation.ValueAnimator.REVERSE;


/**
 * Description TODO
 * Author ZhangChenglong
 * Date 18/7/13 10:37
 */

public class CharmView extends FrameLayout {
    
    
    private ImageView titleView, leftStar, rightStar;
    private ImageView titleLight, textLight;
    private TextView textView;
    
    
    public CharmView(Context context) {
        super(context);
        init(context, null);
    }
    
    public CharmView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    
    public CharmView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    
    void init(Context context, @Nullable AttributeSet att) {
        inflate(context, R.layout.charm_view, this);
        titleView = (ImageView) findViewById(R.id.charm_title);
        leftStar = (ImageView) findViewById(R.id.charm_star_left);
        rightStar = (ImageView) findViewById(R.id.charm_star_right);
        
        titleLight = (ImageView) findViewById(R.id.charm_title_light);
        textLight = (ImageView) findViewById(R.id.charm_text_light);
        
        textView = (TextView) findViewById(R.id.charm_text);
        
        
        showLightAnimator();
    }
    
    /**
     * 显示字灯光效果
     */
    private void showLightAnimator() {
        leftStar.setVisibility(VISIBLE);
        rightStar.setVisibility(VISIBLE);
        
        titleLight.setVisibility(VISIBLE);
        if (alphaAnimation == null) {
            alphaAnimation = new AlphaAnimation(0, 1);
            alphaAnimation.setDuration(500);
            alphaAnimation.setRepeatMode(REVERSE);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setRepeatCount(2);
            leftStar.setAnimation(alphaAnimation);
            rightStar.setAnimation(alphaAnimation);
        }
        alphaAnimation.start();
        
        if (translationY == null) {
            translationY = new TranslateAnimation(-320, 60, 0, 0);
            translationY.setDuration(1000);
            translationY.setFillAfter(false);
//            translationY.start();
    
            translationY.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
            
                }
        
                @Override
                public void onAnimationEnd(Animation animation) {
                    titleLight.setVisibility(GONE);
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            textLight();
                        }
                    }, 1000);
            
                }
        
                @Override
                public void onAnimationRepeat(Animation animation) {
            
                }
            });
        }
        
        textLight.startAnimation(translationY);
    }
    
    
    TranslateAnimation translationText;
    TranslateAnimation translationY;
    AlphaAnimation alphaAnimation;
    
    private void textLight() {
        textLight.setVisibility(VISIBLE);
        if (translationText == null) {
            translationText = new TranslateAnimation(112, 460, 0, 0);
    
            translationText.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
            
                }
        
                @Override
                public void onAnimationEnd(Animation animation) {
                    textLight.setVisibility(GONE);
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showLightAnimator();
                        }
                    }, 2000);
                }
        
                @Override
                public void onAnimationRepeat(Animation animation) {
            
                }
            });
            translationText.setFillAfter(false);
            translationText.setDuration(500);
        }
        textLight.startAnimation(translationText);
    }
    
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        
        
    }
    
   
    
    Scroller mScroller = new Scroller(getContext());
    
    //平滑滚动到指定位置
    private void smoothScrollTo(int destX, int dextY) {
        int scrollX = getScrollX();
        int deltaX = destX - scrollX;
        //1000ms内滑向destX
        mScroller.startScroll(scrollX, 0, deltaX, 0, 1000);
        invalidate();
    }
    
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
