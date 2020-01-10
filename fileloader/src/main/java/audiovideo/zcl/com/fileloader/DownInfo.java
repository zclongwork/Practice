package audiovideo.zcl.com.fileloader;

import java.io.Serializable;

/**
 * TODO Description
 *
 * @author zcl
 * @since 2019-11-15
 */
public class DownInfo implements Serializable {
    private static final long serialVersionUID = -6625368237199002999L;
    private String url;
    private String filePath;
    private String fileName;
    private String md5;
    private boolean md5Check;
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String getMd5() {
        return md5;
    }
    
    public void setMd5(String md5) {
        this.md5 = md5;
    }
    
    public boolean isMd5Check() {
        return md5Check;
    }
    
    public void setMd5Check(boolean md5Check) {
        this.md5Check = md5Check;
    }
}
