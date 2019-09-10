package com.zcl.library.util.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * BaseAdapter抽象，方便在使用AdapterView时创建Adapter类，实现自动的视图复用。
 *
 * @author zcl
 * @since 2019-7-31
 */
public abstract class SimpleBaseAdapter<T> extends BaseListAdapter<T> {

    /**
     * Constructor
     * @param context
     */
    protected SimpleBaseAdapter(Context context) {
        super(context);
    }
    
    /**
     * Constructor
     * @param context
     * @param data
     */
    protected SimpleBaseAdapter(Context context, List<T> data) {
        super(context, data);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(getItemViewLayoutRes(), parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else if (convertView.getTag() instanceof ViewHolder){
            holder = (ViewHolder) convertView.getTag();
        } else {
            return convertView;
        }
        return getItemView(position, convertView, holder);
    }
    
    /**
     * Get the resource id of layout xml file for item view.
     * @return
     */
    protected abstract int getItemViewLayoutRes();
    
    /**
     * Get item view.
     * @param position Position of the item.
     * @param convertView Old view for reuse. Assured non-null.
     * @param holder {@link ViewHolder}
     * @return
     */
    protected abstract View getItemView(int position, View convertView, ViewHolder holder);
    
}
