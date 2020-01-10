package com.zcl.practice.ext;

/**
 * 下载状态事件
 * @author ZhangChenglong
 * @since 2019-11-01
 */
public class DownloadEvent {
    public static final int EVENT_DOWNLOAD_COMPLETE = 0;
    public static final int EVENT_DOWNLOADING = 1;
    public static final int EVENT_DOWNLOAD_CANCELLED = 2;

    public final int event;
    public final DownloadData data;

    public DownloadEvent(int event, DownloadData data) {
        this.event = event;
        this.data = data;
    }
}
