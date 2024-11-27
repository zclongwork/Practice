package com.sign;

import java.util.Iterator;
import java.util.TreeMap;

public class SignatureUtil {

    public static String createClientSignV4(
            TreeMap<String, Object> paramsTreeMap, String privateKeyStr) {
        String sign = "";
        StringBuffer buffer = new StringBuffer();
        if (null != paramsTreeMap) {
            // 遍历添加所有传值
            Iterator<String> itKeys = paramsTreeMap.keySet().iterator();
            while (itKeys.hasNext()) {
                String key = itKeys.next();
                Object value = paramsTreeMap.get(key);
                if (null != value) {
                    if (value instanceof String) {
                        buffer.append(value);
                    } else if (value instanceof Boolean) {
                        buffer.append(value.toString());
                    } else if (value instanceof Integer) {
                        buffer.append(String.valueOf(value));
                    } else if (value instanceof Double) {
                        buffer.append(String.valueOf(value));
                    } else if (value instanceof Float) {
                        buffer.append(String.valueOf(value));
                    } else if (value instanceof Long) {
                        buffer.append(String.valueOf(value));
                    } else if (value instanceof Short) {
                        buffer.append(String.valueOf(value));
                    }
                }
            }
            // 添加一级签名
            buffer.append(privateKeyStr);
        }
        // md5一级签名
        sign = CorytTool.ecodeByMD5(buffer.toString());
        // md5二级签名
        sign = CorytTool.ecodeByMD5(sign + CorytTool.getCipher());
        return sign;
    }


}
