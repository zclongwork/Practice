package audiovideo.zcl.com.fileloader;

/**
 * 下载相关数据
 * @author ZhangChenglong
 * @since 2019-11-01
 */
public class DownloadData {
    
    /** 文件地址 */
    private String url;
    
    /** 文件名称 */
    private String mName;
    /** 文件描述 */
    private String mDescription;
    /** 文件md5 */
    private String md5;
    /** 优先级 */
    private int priority;
    
    private String folder;

    /**
     * 构造方法
     *
     * @param url download url
     */
    public DownloadData(String url) {
        this.url = url;
    }


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
    
    public String getMd5() {
        return md5;
    }
    
    public void setMd5(String md5) {
        this.md5 = md5;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    public String getFolder() {
        return folder;
    }
    
    public void setFolder(String folder) {
        this.folder = folder;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DownloadData:[url=").append(url);
        sb.append(", desc=").append(mDescription);
        sb.append(", name=").append(mName);
        sb.append(", md5=").append(md5);
        sb.append(", priority=").append(priority);
        sb.append(", folder=").append(folder);
        sb.append("]");
        return sb.toString();
    }
}
