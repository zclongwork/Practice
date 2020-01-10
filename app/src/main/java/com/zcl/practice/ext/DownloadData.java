package com.zcl.practice.ext;

import android.net.Uri;

/**
 * 下载相关数据
 * @author ZhangChenglong
 * @since 2019-11-01
 */
public class DownloadData {
    /** download id*/
    private final long mDownloadId;
    /**Download Uri**/
    private final Uri mUri;

    /**当前下载进度**/
    private long mCurrentBytes;
    /**下载文件总大小**/
    private long mTotalBytes;
//    /**下载speed**/
//    private long mSpeedBytes;
    /**下载的状态**/
    private int mStatus = DownloadHelper.STATUS_UNKOWN;
    /** 文件名称 */
    private String mName;
    /** 文件描述 */
    private String mDescription;

    /**
     * 构造方法
     *
     * @param id download id
     */
    public DownloadData(long id) {
        mDownloadId = id;
        mUri = DownloadHelper.getUriById(id);
    }

    /**
     * 读取下载uri
     * @return 下载uri
     */
    public Uri getUri() {
        return mUri;
    }

    /**
     * @return the download id
     */
    public long getDownloadId() {
        return mDownloadId;
    }

    /**
     * 设置下载状态
     * @param status status
     */
    public void setStatus(int status) {
        mStatus = status;
    }

    /**
     * 读取下载状态
     * @return status
     */
    public int getStatus() {
        return mStatus;
    }

    /**
     * 设置当前下载量
     * @param currentBytes current bytes
     */
    public void setCurrentBytes(long currentBytes) {
        mCurrentBytes = currentBytes;
    }

    /**
     * 获取当前下载的量
     * @return current bytes
     */
    public long getCurrentBytes() {
        return mCurrentBytes;
    }

    /**
     * 设置下载文件总大小
     * @param totalBytes file size
     */
    public void setTotalBytes(long totalBytes) {
        mTotalBytes = totalBytes;
    }

    /**
     * 获取下载文件总大小
     * @return file size
     */
    public long getTotalBytes() {
        return mTotalBytes;
    }

//    /**
//     * 设置下载速度
//     * @param speed speed
//     */
//    public void setSpeed(long speed) {
//        mSpeedBytes = speed;
//    }
//
//    /**
//     * 获取下载速度
//     * @return speed
//     */
//    public long getSpeed() {
//        return mSpeedBytes;
//    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DownloadData:[uri=").append(mUri);
        sb.append(", name=").append(mName);
        sb.append(", desc=").append(mDescription);
        sb.append(", currentBytes=").append(mCurrentBytes);
        sb.append(", totalBytes=").append(mTotalBytes);
//        sb.append(", speed=").append(mSpeedBytes);
        sb.append(", status=").append(mStatus);
        sb.append("]");
        return sb.toString();
    }
}
