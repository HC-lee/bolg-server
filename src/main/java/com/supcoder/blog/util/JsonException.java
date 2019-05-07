package com.supcoder.blog.util;

/**
 * @Author huqi
 * @Description:
 * @Date Create In 17:16 2018/6/28 0028
 */
public class JsonException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public JsonException(String msg) {
        super(msg);
    }

    public JsonException(Throwable t) {
        super(t);
    }

    public JsonException(String msg, Throwable t) {
        super(msg, t);
    }
}
