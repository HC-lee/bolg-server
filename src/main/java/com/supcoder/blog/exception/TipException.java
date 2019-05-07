package com.supcoder.blog.exception;

/**
 * @author lee
 */
public class TipException extends RuntimeException {

	private static final long serialVersionUID = -6773372105984590040L;

	/**
	 * 自定义的错误码
	 */
	private Integer code;

	private ErrorCodeEnum errorCodeEnum;

	public TipException(String message) {
		super(message);
	}

	public TipException(ErrorCodeEnum errorCodeEnum) {
		super(errorCodeEnum.getMsg());
		this.code = errorCodeEnum.getCode();
		this.errorCodeEnum = errorCodeEnum;
	}

	public TipException(int code, String msg){
		super(msg);
		this.code = code;
	}


	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public ErrorCodeEnum getErrorCodeEnum() {
		return errorCodeEnum;
	}
}
