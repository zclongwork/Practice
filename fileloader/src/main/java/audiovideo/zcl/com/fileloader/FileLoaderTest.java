package audiovideo.zcl.com.fileloader;

import android.content.Context;
import android.util.Log;

import com.path.android.jobqueue.AsyncAddCallback;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.JobStatus;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;


/**
 * TODO Description
 *
 * @author zcl
 * @since 2019-11-07
 */
public class FileLoaderTest {
    
    private static final String TAG = "FileLoaderTest";
    private static final boolean DEBUG = true;
    
    private JobManager jobManager;
    
    private static FileLoaderTest instance = new FileLoaderTest();
    
    
    private FileLoaderTest() {
    
    }
    
    private long jobid;
    
 
    public static FileLoaderTest getInstance() {
        return instance;
    }
    
    public void addJob(Job job) {
        if (jobManager != null) {
            jobManager.addJobInBackground(job, new AsyncAddCallback() {
                @Override
                public void onAdded(long jobId) {
                    jobid = jobId;
                    Log.d(TAG,  "onAdded jobId: " + jobId);
                }
            });
        }
    }
    
    
    public void getJobStatus() {
        
        if (jobManager != null) {
            JobStatus status = jobManager.getJobStatus(jobid, true);
    
            Log.d(TAG, jobid + " status: " + status);
        }
        
    }
    
    
    
    /**
     *
     * @param context application context;
     */
    public void configureJobManager(Context context) {

//        Configuration configuration = new Configuration.Builder(context.getApplicationContext())
//                .minConsumerCount(1)//always keep at least one consumer alive
//                .maxConsumerCount(3)//up to 3 consumers at a time
//                .loadFactor(3)//3 jobs per consumer
//                .consumerKeepAlive(120)//wait 2 minute
//                .build();
    
    
        Configuration configuration = new Configuration.Builder(context)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";

                    @Override
                    public boolean isDebugEnabled() {
                        return DEBUG;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }
                })
                //.minConsumerCount(0) // always keep at least one consumer alive
                //.maxConsumerCount(3) // up to how many consumers at a time
                //.loadFactor(3) // jobs per consumer
                .consumerKeepAlive(10) // wait time in seconds
                .build();
        jobManager = new JobManager(context, configuration);
        
    }
    
}
