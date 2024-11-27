package com.sign;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CorytTool {

    private final static char hexDigits[] = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    // 签名验证的密钥
    public static String getCipher() {
//        if (TextUtils.isEmpty(AccountManager.getLoginInfo().getStaticKey())) {
//            return "123456qwert";
//        } else {
//            return AccountManager.getLoginInfo().getStaticKey();
//        }
        return "qwer1234";
    }

    /**
     * MD5加密摘要计算
     *
     * @param originstr
     * @return string
     */
    public static String ecodeByMD5(String originstr) {
        String result = null;
        if (originstr != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] source = originstr.getBytes("utf-8");
                md.update(source);
                byte[] tmp = md.digest();
                char[] str = new char[32];
                for (int i = 0, j = 0; i < 16; i++) {
                    byte b = tmp[i];
                    str[j++] = hexDigits[b >>> 4 & 0xf];
                    str[j++] = hexDigits[b & 0xf];
                }
                result = new String(str);// 结果转换成字符串用于返回
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }

        }
        return result;
    }

}
