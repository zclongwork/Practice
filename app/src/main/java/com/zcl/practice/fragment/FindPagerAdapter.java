package com.zcl.practice.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.util.SparseArray;

import java.lang.ref.WeakReference;
import java.util.List;


/**
 * Description 发现Adapter
 * Author zcl
 * Date 2020-01-13
 */
public class FindPagerAdapter extends FragmentStatePagerAdapter {

    private List<TitleBean> fragmentTypeList;

    private Context mContext;
    private SparseArray<WeakReference<Fragment>> fragmentPool = new SparseArray<>();
    private int selectedIndex = -1;

    @Override
    public Fragment getItem(int position) {
        return getPagerFragment(position);
    }

    public FindPagerAdapter(@NonNull Context context, FragmentManager fm,
                            @NonNull List<TitleBean> fragmentTypeList) {
        super(fm);
        this.mContext = context;
        this.fragmentTypeList = fragmentTypeList;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (fragmentTypeList != null) {
            count = fragmentTypeList.size();
        }
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTypeList.get(position).getTitle();
    }

    private Fragment getPagerFragment(int position) {
        Fragment baseFragment = findByIndex(position);
        if (baseFragment == null) {
            baseFragment = createFragment(fragmentTypeList.get(position));
            fragmentPool.append(position, new WeakReference<>(baseFragment));
        }
        return baseFragment;
    }

    private Fragment createFragment(TitleBean titleBean) {
        int type = titleBean.getType();
        Log.d("FindPagerAdapter", "createFragment "+ type );
        Fragment livePager = null;
        switch (type) {
            //1=>小视频, 2=>咨询, 3=>广场
            case 1:
                livePager = new FragmentDemo1();
                break;
            case 2:
                livePager = new FragmentDemo2();
                break;
            case 3:
                livePager = new FragmentDemo2();
                break;
            default:
                break;
        }
        return livePager;
    }

    private Fragment findByIndex(int position) {
        WeakReference<Fragment> fragmentRef = fragmentPool.get(position);
        if (fragmentRef == null) {
            return null;
        }

        return fragmentRef.get();
    }


}
