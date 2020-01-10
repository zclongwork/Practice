package com.zcl.practice.ext;

/**
 * 下载状态回调
 * @author ZhangChenglong
 * @since 2019-11-01
 */
public interface DownloadListener {
    void onChanged(DownloadData data);
}
