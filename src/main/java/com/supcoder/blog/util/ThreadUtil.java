package com.supcoder.blog.util;

/**
 * @Author huqi
 * @Description:
 * @Date Create In 17:30 2018/6/28 0028
 */
public class ThreadUtil {
    public ThreadUtil() {
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception var3) {
            throw new RuntimeException(var3.getMessage(), var3);
        }
    }

    public static void sleep(long millis, int nanos) {
        try {
            Thread.sleep(millis, nanos);
        } catch (Exception var4) {
            throw new RuntimeException(var4.getMessage(), var4);
        }
    }
}