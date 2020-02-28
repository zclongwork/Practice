package com.zcl.practice.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zcl.practice.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

public class FragmentDemoActivity extends FragmentActivity {
    private ViewPager mViewPager;
    FindPagerAdapter mAdapter;
    private MagicIndicator mIndicator;

    public List<TitleBean> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment_demo);
        init();
    }

    private void init() {

        mFragmentList.add(new TitleBean("Demo1", 1));
        mFragmentList.add(new TitleBean("Demo2", 2));
        mFragmentList.add(new TitleBean("Demo3", 3));
        mIndicator = findViewById(R.id.indicator);
        mViewPager = findViewById(R.id.viewPager);
        mAdapter = new FindPagerAdapter(this, getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mAdapter);
        initIndicator();

    }

    private CommonNavigatorAdapter navigatorAdapter;

    private void initIndicator() {
        CommonNavigator navigator = new CommonNavigator(this);

        navigator.setFollowTouch(false);
        navigator.setEnablePivotScroll(true);

        navigator.setScrollPivotX(0.5f);
        navigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView titleView = new SimplePagerTitleView(context);
                titleView.setNormalColor(Color.parseColor("#A2A2A2"));
                titleView.setSelectedColor(Color.parseColor("#000000"));
                titleView.setText(mFragmentList.get(index).getTitle());
                titleView.getPaint().setFakeBoldText(true);


                titleView.setTextSize(14);
                TitleBean title = mFragmentList.get(index);

                titleView.setPadding(10, 0, 10, 0);
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });

                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator lineIndicator = new LinePagerIndicator(context);
                lineIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                lineIndicator.setLineWidth(16);
                lineIndicator.setLineHeight(4);
                lineIndicator.setColors(Color.parseColor("#fa3e3e"));
                lineIndicator.setYOffset(5);
                return lineIndicator;
            }
        };
        navigator.setAdapter(navigatorAdapter);
        mIndicator.setNavigator(navigator);
        ViewPagerHelper.bind(mIndicator, mViewPager);
    }


}
