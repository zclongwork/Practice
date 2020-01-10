package com.zcl.practice.ext;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.zcl.practice.App;
import com.zcl.practice.util.APIUtils;
import com.zcl.practice.util.FileUtils;
import com.zcl.practice.util.Utility;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 下载管理
 * @author ZhangChenglong
 * @since 2019-11-01
 */
public enum DownloadHelper {
    /** Single instance */
    INSTANCE;

    public static DownloadHelper getInstance() {
        return INSTANCE;
    }

    private static final String TAG = "DownloadHelper";
    private static final boolean DEBUG = App.GLOBAL_DEBUG;

    private static final String DOWNLOAD_SUBDIR = "6rooms";
    /** This is a hack of system's hidden content uri path. */
    private static final String DOWNLOAD_URI = "content://downloads/my_downloads/";

    public static final String MIME_APK = "application/vnd.android.package-archive";

    public static final int STATUS_UNKOWN = 0;
    public static final int INVALID_ID = -1;

    private Context mContext;
    private DownloadManager mDownloadManager;
    /** Uri与DownloadObserver的映射表 */
    private Map<Uri, DownloadObserver> mObserverMap = new HashMap<>();

    private DownloadHelper() {
        mContext = App.getAppContext();
        mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    /**
     * Add a new download to the download manager.
     * @param title title for the file.
     * @param desc description for the file.
     * @param url download url for the file.
     * @param fileName file name to be saved on storage.
     * @return a download ID, given by the download manager.
     */
    @SuppressLint("NewApi")
    public long addDownload(String title, String desc, String url, String fileName) {
        long downloadId = INVALID_ID;
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(url) || TextUtils.isEmpty(fileName)) {
            return downloadId;
        }
        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (downloadDir == null || (downloadDir.exists() && !downloadDir.isDirectory())) {
            if (DEBUG) {
                Log.w(TAG, "System's public download dir is not available!");
            }
            return downloadId;
        }
        File subDir = new File(downloadDir, DOWNLOAD_SUBDIR);
        if (!FileUtils.ensureDirExists(subDir)) {
            if (DEBUG) {
                Log.w(TAG, "Download dir " + subDir.getAbsolutePath() + " does not exist!");
            }
            return downloadId;
        }
        try {
//            DownloadManager.Request req = new DownloadManager.Request(Uri.parse(url))
//                    .setTitle(title)
//                    .setDescription(desc)
//                    .setMimeType(MIME_APK)
//                    .setDestinationUri(Uri.fromFile(new File(subDir, fileName)))
//                    .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
//            if (APIUtils.hasHoneycomb()) {
//                req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
//            }
    
    
            DownloadManager.Request req = new DownloadManager.Request(Uri.parse(url))
                    .setDestinationUri(Uri.fromFile(new File(subDir, fileName)));
//            if (APIUtils.hasHoneycomb()) {
//                req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
//            }
            
            downloadId = mDownloadManager.enqueue(req);
            if (DEBUG) {
                Log.d(TAG, "download added, id=" + downloadId);
            }
        } catch (Exception e) {
            if (DEBUG) {
                e.printStackTrace();
            }
        }
        return downloadId;
    }

    /**
     * Cancel downloads. Downloads will be stopped regardless of the status, and files will be removed
     * if there are any, partial or complete.
     * @param ids IDs of the downloads to remove.
     * @return number of downloads actually removed.
     */
    public int cancelDownload(long... ids) {
        return mDownloadManager.remove(ids);
    }

    /**
     * 注册监听器
     * @param id 下载ID
     * @param listener listener
     */
    public void registerObserver(long id, DownloadListener listener) {
        if (listener == null) {
            if (DEBUG) {
                Log.e(TAG, "registerObserver, listener is null.");
            }
            return;
        }
        if (id == INVALID_ID) {
            if (DEBUG) {
                Log.e(TAG, "registerObserver, id is invalid.");
            }
            return;
        }

        Uri uri = getUriById(id);
        if (uri == null) {
            return;
        }
        DownloadObserver observer = mObserverMap.get(uri);
        if (observer == null) {
            observer = new DownloadObserver(id);
            mObserverMap.put(uri, observer);
            mContext.getContentResolver().registerContentObserver(uri, true, observer);
        }

        observer.addListener(listener);
    }

    /**
     * 删除observer
     * @param id 下载ID
     * @param listener 监听下载状态的listener
     */
    public void unregisterObserver(long id, DownloadListener listener) {
        Uri uri = getUriById(id);
        if (uri == null) {
            return;
        }

        DownloadObserver observer = mObserverMap.get(uri);
        if (observer == null) {
            return;
        }

        if (listener == null) {
            observer.clearListeners();
        } else {
            observer.removeListener(listener);
        }
        if (observer.isListenersEmpty()) {
            mContext.getContentResolver().unregisterContentObserver(observer);
            mObserverMap.remove(uri);
        }
    }

    /**
     * 删除observer
     * @param id 下载ID
     */
    public void unregisterObserver(long id) {
        unregisterObserver(id, null);
    }

    /**
     * 根据Uri取得ID
     *
     * @param uri    Uri
     * @return       ID
     */
    private long getIdFromUri(Uri uri) {
        long id = INVALID_ID;
        if (uri != null) {
            id = ContentUris.parseId(uri);
        } else {
            if (DEBUG) {
                Log.e(TAG, "getIdFromUri(uri == null)");
            }
        }
        return id;
    }

    public static Uri getUriById(long id) {
        if (id != INVALID_ID) {
            return Uri.parse(DOWNLOAD_URI + id);
        }
        return null;
    }

    /**
     * 获取下载数据
     * @param id download id
     * @return {@link DownloadData}
     */
    public DownloadData getDownloadData(long id) {
        if (id == INVALID_ID) {
            return null;
        }
        DownloadData data = new DownloadData(id);
        fillDownloadData(data);
        return data;
    }

    /**
     * Fill all the data for the download.
     * @param data download data.
     */
    public void fillDownloadData(DownloadData data) {
        if (data == null || data.getDownloadId() == INVALID_ID) {
            return;
        }
        Cursor c = mDownloadManager.query(new DownloadManager.Query().setFilterById(data.getDownloadId()));
        try {
            if (c != null && c.moveToFirst()) {
                fillData(data, c);
            } else {
                data.setStatus(STATUS_UNKOWN);
                data.setCurrentBytes(0);
                data.setTotalBytes(-1);
            }
        } catch (Throwable t) {
            if (DEBUG) {
                t.printStackTrace();
            }
        } finally {
            Utility.closeSafely(c);
        }
    }

    private void fillData(DownloadData data, Cursor c) {
        data.setStatus(c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS)));
        int index = c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
        if (index > -1) {
            data.setTotalBytes(c.getLong(index));
        }
        index = c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
        if (index > -1) {
            data.setCurrentBytes(c.getLong(index));
        }
        index = c.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION);
        if (index > -1) {
            data.setDescription(c.getString(index));
        }
    }

    @SuppressLint("NewApi")
    public Uri getDownloadedFileUri(long id) {
        if (APIUtils.hasHoneycomb()) {
            return mDownloadManager.getUriForDownloadedFile(id);
        }
        Cursor c = null;
        try {
            c = mDownloadManager.query(new DownloadManager.Query().setFilterById(id));
            if (c == null) {
                return null;
            }
            while (c.moveToFirst()) {
                String file = c.getString(c.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI));
                return Uri.parse(file);
            }
        } catch (Throwable t) {
            if (DEBUG) {
                t.printStackTrace();
            }
        } finally {
            Utility.closeSafely(c);
        }
        return null;
    }

    @SuppressLint("NewApi")
    public String getDownloadedFileMimeType(long id) {
        if (APIUtils.hasHoneycomb()) {
            return mDownloadManager.getMimeTypeForDownloadedFile(id);
        }
        Cursor c = null;
        try {
            c = mDownloadManager.query(new DownloadManager.Query().setFilterById(id));
            if (c == null) {
                return null;
            }
            while (c.moveToFirst()) {
                return c.getString(c.getColumnIndexOrThrow(DownloadManager.COLUMN_MEDIA_TYPE));
            }
        } finally {
            Utility.closeSafely(c);
        }
        // downloaded file not found or its status is not 'successfully completed'
        return null;
    }

    private List<DownloadData> getDownloadsByStatus(int status) {
        List<DownloadData> list = null;
        Cursor c = null;
        try {
            c = mDownloadManager.query(new DownloadManager.Query().setFilterByStatus(status));
            if (c != null && c.moveToFirst()) {
                list = new ArrayList<>(c.getCount());
                do {
                    DownloadData data = new DownloadData(c.getLong(
                            c.getColumnIndexOrThrow(DownloadManager.COLUMN_ID)));
                    fillData(data, c);
                    list.add(data);
                } while (c.moveToNext());
            }
        } catch (Throwable t) {
            if (DEBUG) {
                t.printStackTrace();
            }
        } finally {
            Utility.closeSafely(c);
        }
        return list;
    }

    /**
     * 获取未完成的下载列表
     * @return
     */
    public List<DownloadData> getUnfinishedDownloads() {
        return getDownloadsByStatus(DownloadManager.STATUS_PENDING | DownloadManager.STATUS_PAUSED
                | DownloadManager.STATUS_RUNNING);
    }

    /**
     * 获取已完成的下载列表，包含成功和失败的下载。
     * @return
     */
    public List<DownloadData> getFinishedDownloads() {
        return getDownloadsByStatus(DownloadManager.STATUS_FAILED | DownloadManager.STATUS_SUCCESSFUL);
    }
}
