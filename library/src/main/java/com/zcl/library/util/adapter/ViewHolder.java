package com.zcl.library.util.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * View holder to utilize the view recycling in adapter.
 * @author zcl
 * @since 2019-7-31
 */
public class ViewHolder {
    /** view map */
    private SparseArray<View> views = new SparseArray<View>();
    /** convertView */
    private View convertView;

    public ViewHolder(View convertView) {
        this.convertView = convertView;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int resId) {
        View v = views.get(resId);
        if (null == v) {
            v = convertView.findViewById(resId);
            views.put(resId, v);
        }
        return (T) v;
    }
}
