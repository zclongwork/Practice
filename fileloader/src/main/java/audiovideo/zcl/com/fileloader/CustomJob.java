package audiovideo.zcl.com.fileloader;

import android.text.TextUtils;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

/**
 * TODO Description
 *
 * @author zcl
 * @since 2019-11-18
 */
public class CustomJob extends Job {
    
    private Work work;
    
    private static Params getParams(Work work) {
        Config config = work.getConfig();
        
        Params params = new Params(config.getPriority());
        if (config.isPersist()) {
            params.persist();
        }
        if (config.isRequiresNetwork()) {
            params.requireNetwork();
        }
        if (!TextUtils.isEmpty(config.getGroupId())) {
            params.groupBy(config.getGroupId());
        }
        
        return params;
    }
    
    
    CustomJob(Work work) {
        super(getParams(work));
        this.work = work;
    }
    
    @Override
    public void onAdded() {
        work.onAdd();
    }
    
    @Override
    public void onRun() throws Throwable {
        work.onRun();
    }
    
    @Override
    protected void onCancel() {
        work.onCancel();
    }
}
