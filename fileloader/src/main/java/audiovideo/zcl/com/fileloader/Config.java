package audiovideo.zcl.com.fileloader;

/**
 * TODO Description
 *
 * @author zcl
 * @since 2019-11-18
 */
public class Config {
    
    private int priority;
    private boolean persist;
    private boolean requiresNetwork;
    private String groupId;
    private long delayMs;
    
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    public boolean isPersist() {
        return persist;
    }
    
    public void setPersist(boolean persist) {
        this.persist = persist;
    }
    
    public boolean isRequiresNetwork() {
        return requiresNetwork;
    }
    
    public void setRequiresNetwork(boolean requiresNetwork) {
        this.requiresNetwork = requiresNetwork;
    }
    
    public String getGroupId() {
        return groupId;
    }
    
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    
    public long getDelayMs() {
        return delayMs;
    }
    
    public void setDelayMs(long delayMs) {
        this.delayMs = delayMs;
    }
}
