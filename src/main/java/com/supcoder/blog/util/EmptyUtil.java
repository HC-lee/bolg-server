package com.supcoder.blog.util;

/**
 * @Author huqi
 * @Description:
 * @Date Create In 17:28 2018/6/28 0028
 */
import java.util.Collection;
import java.util.Map;

public class EmptyUtil {
    public EmptyUtil() {
    }

    public static boolean isEmpty(String s, boolean trim) {
        if (s == null) {
            return true;
        } else if (s.length() == 0) {
            return true;
        } else {
            return trim && s.trim().length() == 0;
        }
    }

    public static boolean isEmpty(String s) {
        return isEmpty(s, true);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.size() == 0;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }
}

