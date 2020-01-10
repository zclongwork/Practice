package audiovideo.zcl.com.fileloader;

/**
 * TODO Description
 *
 * @author zcl
 * @since 2019-11-18
 */
abstract public class Work {
    
    Config config;
    
    public Work(Config config) {
        this.config = config;
    }
    
    Config getConfig(){
        return config;
    }
    
    
    abstract public void onAdd();
    
    abstract public void onRun();
    
    abstract public void onCancel();
    
}
