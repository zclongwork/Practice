package audiovideo.zcl.com.fileloader;


import android.os.SystemClock;
import android.util.Log;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.io.File;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okio.BufferedSink;
import okio.Okio;

/**
 * TODO Description
 *
 * @author zcl
 * @since 2019-11-07
 */
public class DownloadJob extends Job {
    
    public static String TAG = "DownloadJob";
    
    private DownInfo info;
    private int level;
    
    
    public DownloadJob(DownInfo info, int levele) {
//        super(new Params(Priority.HIGH).persist());
        super(new Params(Priority.HIGH).persist().groupBy(TAG).requireNetwork());
        this.info = info;
        this.level = levele;
    }
    
    
    
    @Override
    public void onAdded() {
        Log.d(TAG, "onAdded ");
    }


    
    @Override
    public void onRun() throws Throwable {
        Log.i(TAG, "onRun " + level);
        Thread.sleep(1000);
    
        Log.d(TAG, "开始运行...");
    
        int i = 0;
        while (true) {
            i++;
        
            SystemClock.sleep(2000);
            Log.d(TAG, String.valueOf(i));
            if (i == 10)
                break;
        }
    
        Log.d(TAG, "完成");
        
    }
    
    @Override
    protected void onCancel() {
    
    }
    
    
    private void startDown(DownInfo downInfo) throws IOException {
        //todo sd检查
        File path = new File(downInfo.getFilePath());
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, downInfo.getFileName());
    
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downInfo.getUrl())
                .build();
        okhttp3.Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            String fileLength = response.headers().get("Content-Length");
            BufferedSink sink = null;
            //下载文件到本地
            try {
                sink = Okio.buffer(Okio.sink(file));
                sink.writeAll(response.body().source());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (sink != null) {
                        sink.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        
    }
    
}
