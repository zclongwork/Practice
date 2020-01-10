package audiovideo.zcl.com.fileloader;

import com.path.android.jobqueue.JobManager;

/**
 * TODO Description
 *
 * @author zcl
 * @since 2019-11-18
 */
public class WorkManager {
    
    JobManager jobManager;
    
    public void addWorkInBackground(Work work) {
    
    
        jobManager.addJobInBackground(new CustomJob(work));
        
    }
    
    
//    public void add Work
    
    
    
}
