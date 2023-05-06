package com.zcl.library.util;

public class StringUtils {


    /**
     * 首字母变大写
     * @param original 原始字符
     * @return 变大写字符
     */
    public static String capital(String original) {
        StringBuilder builder = new StringBuilder();
        String[] arr = original.split(" ");
        for (String str : arr) {
            if (str.length() > 0) {
                builder.append(str.substring(0, 1).toUpperCase()).append(str.substring(1));
            }
            builder.append(" ");
        }
        return builder.toString();
    }
}
