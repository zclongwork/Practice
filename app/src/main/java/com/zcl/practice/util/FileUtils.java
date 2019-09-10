package com.zcl.practice.util;

import android.util.Log;

import java.io.File;
import java.util.List;

/**
 * Description TODO
 * Author ZhangChenglong
 * Date 17/12/22 11:14
 */

public class FileUtils {
    
    /**
     * 递归查找文件
     * @param baseDirName  查找的文件夹路径
     * @param suffixName  文件后缀名
     * @param fileList  查找到的文件路径集合
     */
    public static void findFiles(String baseDirName, String suffixName, List<String> fileList) {
        
        File baseDir = new File(baseDirName);       // 创建一个File对象
        if (!baseDir.exists() || !baseDir.isDirectory()) {  // 判断目录是否存在
            Log.e("TAB","文件查找失败：" + baseDirName + "不是一个目录！");
        }
        String tempName = null;
        //判断目录是否存在
        File tempFile;
        File[] files = baseDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            tempFile = files[i];
            if(tempFile.isDirectory()){
                findFiles(tempFile.getAbsolutePath(), suffixName, fileList);
            }else if(tempFile.isFile()){
                tempName = tempFile.getName();
                if(tempName.endsWith(suffixName)){
                    // 匹配成功，将文件名添加到结果集
                    fileList.add(tempFile.getAbsolutePath());
                }
            }
        }
    }
    

}
