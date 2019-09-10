package com.zcl.library.util.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 用于ListView的Adapter抽象，提供除getView以外的其它抽象方法的默认实现
 * 
 * @author zcl
 * @since 2019-7-31
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    /** Context */
    protected Context mContext;
    /** Data */
    protected List<T> mData;

    /**
     * Constructor
     * @param context
     */
    protected BaseListAdapter(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Invalid param: Context cannot be null");
        }
        mContext = context;
    }
    
    /**
     * Constructor
     * @param context
     * @param list
     */
    protected BaseListAdapter(Context context, List<T> list) {
        this(context);
        mData = list;
    }
    
    /**
     * Set/Update data
     * @param data
     */
    public void setData(List<T> data) {
        mData = data;
    }
    
    /**
     * Get context
     * @return
     */
    public Context getContext() {
        return mContext;
    }
    
    /**
     * Get {@link LayoutInflater}
     * @return
     */
    public LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    @Override
    public T getItem(int position) {
        if (mData != null && position >= 0 && position < mData.size()) {
            return mData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
}
