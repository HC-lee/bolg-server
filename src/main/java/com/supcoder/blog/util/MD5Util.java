package com.supcoder.blog.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author huqi
 * @Description:
 * @Date Create In 15:37 2018/7/4 0004
 */
public class MD5Util {
    /**
     * md5加密
     *
     * @param source 数据源
     * @return 加密字符串
     */
    public static String md5Encode(String source) {
        if (EmptyUtil.isEmpty(source)) {
            return null;
        }
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ignored) {
        }
        byte[] encode = messageDigest.digest(source.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte anEncode : encode) {
            String hex = Integer.toHexString(0xff & anEncode);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
