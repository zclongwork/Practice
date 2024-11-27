package com.sign;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum JSON_TYPE {
    /**
     * JSONObject
     */
    JSON_TYPE_OBJECT,
    /**
     * JSONArray
     */
    JSON_TYPE_ARRAY,
    /**
     * 不是JSON格式的字符串
     */
    JSON_TYPE_ERROR;


    /***
     *
     * 获取JSON类型
     * 判断规则
     * 判断第一个字母是否为{或[ 如果都不是则不是一个JSON格式的文本
     *
     * @param str
     * @return
     */
    public static JSON_TYPE getJSONType(String str) {
        if (isEmpty(str)) {
            return JSON_TYPE.JSON_TYPE_ERROR;
        }

        final char[] strChar = str.substring(0, 1).toCharArray();
        final char firstChar = strChar[0];

        if (firstChar == '{') {
            return JSON_TYPE.JSON_TYPE_OBJECT;
        } else if (firstChar == '[') {
            return JSON_TYPE.JSON_TYPE_ARRAY;
        } else {
            return JSON_TYPE.JSON_TYPE_ERROR;
        }
    }


    public static boolean isEmpty(String str) {
        if (null == str || "".equals(str)) {
            return true;
        } else if ("".equals(str.trim())) {
            return true;
        } else if ("".equals(replaceBlank(str))) {
            return true;
        } else if ("null".equals(str)) {
            return true;
        } else if ("null".equals(str.trim())) {
            return true;
        }
        return false;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\\\\r|\\\\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
