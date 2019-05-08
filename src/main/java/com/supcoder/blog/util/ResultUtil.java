package com.supcoder.blog.util;


import com.supcoder.blog.exception.ErrorCodeEnum;

/**
 * @author lee
 */
public final class ResultUtil {

	/**
	 * 构建一个带返回数据的结果类
	 * 
	 * @param data
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static JsonResult success(Object data) {
		return success(ErrorCodeEnum.SUCCESS.getCode(),data,"success");
	}

	public static JsonResult<?> error() {
		return success(ErrorCodeEnum.UNKNOWN_ERROR.getCode(),null,"failed");
	}

	public static JsonResult<?> error(Object data) {
		return success(ErrorCodeEnum.UNKNOWN_ERROR.getCode(),data,"failed");
	}

	public static JsonResult<?> error(String msg) {
		return success(ErrorCodeEnum.UNKNOWN_ERROR.getCode(),null,msg);
	}

	public static JsonResult<?> success(int resultCode, String message) {
		return success(resultCode,null,message);
	}

	public static JsonResult<?> success(int resultCode, Object data, String message) {
		JsonResult<Object> result = new JsonResult<Object>();
		result.setResult(resultCode);
		result.setMsg(message);
		result.setData(data);
		return result;
	}

	/**
	 * 构建一个不带返回数据的结果类
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static JsonResult success() {
		return success(0,"success");
	}

	/**
	 * 构建一个自定义错误的返回结果类
	 * 
	 * @param resultCode
	 * @param message
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static JsonResult error(Integer resultCode, String message) {
		JsonResult result = new JsonResult();
		result.setResult(resultCode);
		result.setMsg(message);
		return result;
	}

	public static JsonResult errorWithData(Integer resultCode, String message,String data) {
		JsonResult result = new JsonResult();
		result.setResult(resultCode);
		result.setMsg(message);
		result.setData(data);
		return result;
	}


}
