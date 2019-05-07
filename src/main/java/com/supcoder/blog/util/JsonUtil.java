package com.supcoder.blog.util;

/**
 * @Author huqi
 * @Description:
 * @Date Create In 17:16 2018/6/28 0028
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public class JsonUtil {
    public static final ObjectMapper OM = new ObjectMapper();

    public JsonUtil() {
    }

    public static <T> T readValue(String json, Class<T> type) {
        if (json == null) {
            return null;
        } else {
            try {
                return OM.readValue(json, type);
            } catch (Exception var3) {
                throw new JsonException(var3.getMessage(), var3);
            }
        }
    }

    public static <T> T readValue(String json, TypeReference<T> type) {
        if (json == null) {
            return null;
        } else {
            try {
                return OM.readValue(json, type);
            } catch (Exception var3) {
                throw new JsonException(var3.getMessage(), var3);
            }
        }
    }

    public static <T> T readValue(byte[] content, int offset, int len, TypeReference<T> ref) {
        if (content == null) {
            return null;
        } else {
            try {
                return OM.readValue(content, offset, len, ref);
            } catch (Exception var5) {
                throw new JsonException(var5.getMessage(), var5);
            }
        }
    }

    public static <T> T readValue(byte[] content, int offset, int len, Class<T> type) {
        if (content == null) {
            return null;
        } else {
            try {
                return OM.readValue(content, offset, len, type);
            } catch (Exception var5) {
                throw new JsonException(var5.getMessage(), var5);
            }
        }
    }

    public static <T> String writeValueAsString(T value) {
        try {
            return OM.writeValueAsString(value);
        } catch (Exception var2) {
            throw new JsonException(var2.getMessage(), var2);
        }
    }

    public static <T> void writeValue(OutputStream out, T value) {
        try {
            OM.writeValue(out, value);
        } catch (IOException var3) {
            throw new JsonException(var3.getMessage(), var3);
        }
    }

    public static <T> void writeValue(Writer out, T value) {
        try {
            OM.writeValue(out, value);
        } catch (IOException var3) {
            throw new JsonException(var3.getMessage(), var3);
        }
    }

    public static <T> T convert(Object value, Class<T> type) {
        try {
            return OM.convertValue(value, type);
        } catch (Exception var3) {
            throw new JsonException(var3.getMessage(), var3);
        }
    }

    public static <T> T convert(Object value, TypeReference<T> type) {
        try {
            return OM.convertValue(value, type);
        } catch (Exception var3) {
            throw new JsonException(var3.getMessage(), var3);
        }
    }

    static {
        OM.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        OM.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
}

