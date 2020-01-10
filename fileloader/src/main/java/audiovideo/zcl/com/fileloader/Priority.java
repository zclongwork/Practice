package audiovideo.zcl.com.fileloader;

/**
 * TODO Description
 *
 * @author zcl
 * @since 2019-11-08
 */
public class Priority {
    
    private Priority() {}
    
    /** Low priority, generally for background jobs. */
    public static int LOW = 0;
    /** Medium priority, generally for forground "click-and-go" jobs. */
    public static int MID = 500;
    /** High priority, generally for forground user-interactive jobs. */
    public static int HIGH = 1000;
    /** Immediate priority, generally for forground user-interactive jobs that does not require network. */
    public static int IMMEDIATE = 2000;
}
