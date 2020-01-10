package com.zcl.practice.ext;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;

import com.zcl.practice.App;

import java.util.HashSet;

/**
 * 下载过程的Observer
 * @author ZhangChenglong
 * @since 2019-11-01
 */
public class DownloadObserver extends ContentObserver {
    private static final String TAG = "DownloadObserver";
    private static final boolean DEBUG = App.GLOBAL_DEBUG;

    /** 上一秒钟下载的bytes,用于计算下载速度 **/
    private long mLastBytes = 0;
    /** 记录下载的时间 **/
    private long mLastTime = 0;
    /** 上一次下载状态 **/
    private int mLastStatus = DownloadHelper.STATUS_UNKOWN;

    /** 下载信息 */
    private DownloadData mData;

    /** 监听下载状态的listener集合 */
    private HashSet<DownloadListener> mListeners = new HashSet<>();

    /**
     * 构造函数
     *
     * @param id download id
     */
    public DownloadObserver(long id) {
        super(new Handler());
        mData = new DownloadData(id);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        DownloadHelper.getInstance().fillDownloadData(mData);
        final long now = System.currentTimeMillis();
        if (mLastStatus == mData.getStatus() && (mLastBytes == mData.getCurrentBytes()
                || now - mLastTime < DateUtils.SECOND_IN_MILLIS)) {
            // 控制时间间隔，避免频繁的计算与回调。
            return;
        }

        /*if (DownloadManager.STATUS_RUNNING == mData.getStatus()) {
            // 速度统计很不准，所以只计算DOWNLOADING状态间的下载速度。
            mData.setSpeed(((mData.getCurrentBytes() - mLastBytes) * DateUtils.SECOND_IN_MILLIS) / (now - mLastTime));
        } else {
            mData.setSpeed(0);
        }*/

        if (DEBUG) {
            Log.i(TAG, "onChange, " + mData);
        }
        mLastBytes = mData.getCurrentBytes();
        mLastStatus = mData.getStatus();
        mLastTime = now;

        synchronized (this) {
            DownloadListener[] listeners = new DownloadListener[mListeners.size()];
            mListeners.toArray(listeners);
            for (DownloadListener listener : listeners) {
                listener.onChanged(mData);
            }
        }
    }

    @Override
    public void onChange(boolean selfChange) {
        onChange(selfChange, null);
    }

    /**
     * 设置下载监听器
     *
     * @param listener
     *            listener
     * @return true or false.
     */
    public synchronized boolean addListener(DownloadListener listener) {
        return mListeners.add(listener);
    }

    /**
     * 取消监听
     *
     * @param listener
     *            listener
     * @return true or false.
     */
    public synchronized boolean removeListener(DownloadListener listener) {
        return mListeners.remove(listener);
    }

    /**
     * 取消所有监听
     */
    public synchronized void clearListeners() {
        mListeners.clear();
    }

    /**
     * 判断监听器集合是否为空
     * @return true or false.
     */
    public boolean isListenersEmpty() {
        return mListeners.isEmpty();
    }
}
