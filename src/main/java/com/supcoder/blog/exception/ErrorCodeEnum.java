package com.supcoder.blog.exception;

/**
 * @author lee
 */
public enum ErrorCodeEnum {
    /**
     * 请求成功
     */
    SUCCESS(0, "success"),
    UNKNOWN_ERROR(-1, "未知错误"),
    PARAM_VALID_ERROR(-2, "参数错误"),
    /**
     * 运行时异常
     */
    RUNTIME(1000, "RuntimeException"),
    /**
     * 空指针异常
     */
    NULL_POINTER(1001, "NullPointerException "),
    /**
     * 类型住转换异常
     */
    CLASS_CAST(1002, "ClassCastException"),
    /**
     * IO异常
     */
    IO(1003, "IOException"),
    /**
     * 找不到方法异常
     */
    NO_SUCH_METHOD(1004, "NoSuchMethodException"),
    /**
     * 数组越界异常
     */
    INDEX_OUTOF_BOUNDS(1005, "IndexOutOfBoundsException"),
    /**
     * 400异常
     */
    BAD_REQUEST(400, "Bad Request"),
    /**
     * 404异常
     */
    NOT_FOUND(404, "Not Found"),
    /**
     * 方法不允许异常
     */
    METHOD_BOT_ALLOWED(405, "Method Not Allowed"),
    /**
     * 不可到达异常
     */
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    /**
     * 500异常
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    /**
     * 权限不足
     */
    PERMISSION_DENIED(900, "权限不足"),
    /**
     * 用户未登陆
     */
    NOT_LOGIN(999, "Not Login");



    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
