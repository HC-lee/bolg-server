package com.supcoder.blog.exception;


import com.supcoder.blog.util.JsonResult;
import com.supcoder.blog.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
  常见异常处理并返回相应错误码
  SpringMVC自定义异常对应的status code
  Exception                               HTTP Status Code
  ConversionNotSupportedException         500 (Internal Server Error)
  HttpMessageNotWritableException         500 (Internal Server Error)
  HttpMediaTypeNotSupportedException      415 (Unsupported Media Type)
  HttpMediaTypeNotAcceptableException     406 (Not Acceptable)
  HttpRequestMethodNotSupportedException  405 (Method Not Allowed)
  NoSuchRequestHandlingMethodException    404 (Not Found)
  TypeMismatchException                   400 (Bad Request)
  HttpMessageNotReadableException         400 (Bad Request)
  MissingServletRequestParameterException 400 (Bad Request)
 */


/**
 * @author lee
 */
@Slf4j
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Tip异常返回(业务逻辑异常)
     *
     * @param req {@link HttpServletRequest}
     * @param e   {@link Exception}
     * @return {@link JsonResult}
     */
    @ExceptionHandler(value = TipException.class)
    public JsonResult tipErrorHandler(HttpServletRequest req, Exception e) {
        return ResultUtil.error(e.getMessage());
    }

    /**
     * 运行时异常
     *
     * @param req {@link HttpServletRequest}
     * @param rep {@link HttpServletResponse}
     * @param re  {@link RuntimeException}
     * @return {@link JsonResult}
     */
    @ExceptionHandler(RuntimeException.class)
    public JsonResult runtimeExceptionHandler(HttpServletRequest req, HttpServletResponse rep, RuntimeException re) {
        log.error("---RuntimeException Handler---Host {}, invokes url {},  ERROR: {}", req.getRemoteHost(), req.getRequestURL(), re.getMessage());
        re.printStackTrace();
        rep.setStatus(ErrorCodeEnum.RUNTIME.getCode());
        return ResultUtil.error(ErrorCodeEnum.RUNTIME.getCode(), ErrorCodeEnum.RUNTIME.getMsg());
    }

    /**
     * 空指针异常
     *
     * @param req {@link HttpServletRequest}
     * @param rep {@link HttpServletResponse}
     * @param ex  {@link NullPointerException}
     * @return {@link JsonResult}
     */
    @ExceptionHandler(NullPointerException.class)
    public JsonResult nullPointerExceptionHandler(HttpServletRequest req, HttpServletResponse rep, NullPointerException ex) {
        log.error("---NullPointerException Handler---Host {}, invokes url {},  ERROR: {}", req.getRemoteHost(), req.getRequestURL(), ex.getMessage());
        ex.printStackTrace();
        rep.setStatus(ErrorCodeEnum.NULL_POINTER.getCode());
        return ResultUtil.error(ErrorCodeEnum.NULL_POINTER.getCode(), ErrorCodeEnum.NULL_POINTER.getMsg());
    }

    /**
     * 类型转换异常
     *
     * @param req {@link HttpServletRequest}
     * @param rep {@link HttpServletResponse}
     * @param ex  {@link ClassCastException}
     * @return {@link JsonResult}
     */
    @ExceptionHandler(ClassCastException.class)
    public JsonResult classCastExceptionHandler(HttpServletRequest req, HttpServletResponse rep, ClassCastException ex) {
        log.error("---classCastException Handler---Host {}, invokes url {},  ERROR: {}", req.getRemoteHost(), req.getRequestURL(), ex.getMessage());
        ex.printStackTrace();
        rep.setStatus(ErrorCodeEnum.CLASS_CAST.getCode());
        return ResultUtil.error(ErrorCodeEnum.CLASS_CAST.getCode(), ErrorCodeEnum.CLASS_CAST.getMsg());
    }

    /**
     * IO异常
     *
     * @param req {@link HttpServletRequest}
     * @param rep {@link HttpServletResponse}
     * @param ex  {@link IOException}
     * @return {@link JsonResult}
     */
    @ExceptionHandler(IOException.class)
    public JsonResult classCastExceptionHandler(HttpServletRequest req, HttpServletResponse rep, IOException ex) {
        log.error("---classCastException Handler---Host {}, invokes url {},  ERROR: {}", req.getRemoteHost(), req.getRequestURL(), ex.getMessage());
        ex.printStackTrace();
        rep.setStatus(ErrorCodeEnum.IO.getCode());
        return ResultUtil.error(ErrorCodeEnum.IO.getCode(), ErrorCodeEnum.IO.getMsg());
    }

    /**
     * 未知方法异常
     *
     * @param req {@link HttpServletRequest}
     * @param rep {@link HttpServletResponse}
     * @param ex  {@link NoSuchMethodException}
     * @return {@link JsonResult}
     */
    @ExceptionHandler(NoSuchMethodException.class)
    public JsonResult noSuchMethodExceptionHandler(HttpServletRequest req, HttpServletResponse rep, NoSuchMethodException ex) {
        log.error("---noSuchMethodException Handler---Host {}, invokes url {},  ERROR: {}", req.getRemoteHost(), req.getRequestURL(), ex.getMessage());
        ex.printStackTrace();
        rep.setStatus(ErrorCodeEnum.NO_SUCH_METHOD.getCode());
        return ResultUtil.error(ErrorCodeEnum.NO_SUCH_METHOD.getCode(), ErrorCodeEnum.NO_SUCH_METHOD.getMsg());
    }

    /**
     * 数组越界异常
     *
     * @param req {@link HttpServletRequest}
     * @param rep {@link HttpServletResponse}
     * @param ex  {@link IndexOutOfBoundsException}
     * @return {@link JsonResult}
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public JsonResult indexOutOfBoundsExceptionHandler(HttpServletRequest req, HttpServletResponse rep, IndexOutOfBoundsException ex) {
        log.error("---indexOutOfBoundsException Handler---Host {}, invokes url {},  ERROR: {}", req.getRemoteHost(), req.getRequestURL(), ex.getMessage());
        ex.printStackTrace();
        rep.setStatus(ErrorCodeEnum.INDEX_OUTOF_BOUNDS.getCode());
        return ResultUtil.error(ErrorCodeEnum.INDEX_OUTOF_BOUNDS.getCode(), ErrorCodeEnum.INDEX_OUTOF_BOUNDS.getMsg());
    }

    /**
     * 400相关异常
     *
     * @param rep {@link HttpServletResponse}
     * @param ex  {@link Exception}
     * @return {@link JsonResult}
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, TypeMismatchException.class, MissingServletRequestParameterException.class})
    public JsonResult request400(HttpServletResponse rep, Exception ex) {
        rep.setStatus(ErrorCodeEnum.BAD_REQUEST.getCode());
        return ResultUtil.error(ErrorCodeEnum.BAD_REQUEST.getCode(), ex.getMessage());
    }

    /**
     * 405相关异常
     *
     * @param rep {@link HttpServletResponse}
     * @param ex  {@link Exception}
     * @return {@link JsonResult}
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public JsonResult request405(HttpServletResponse rep, Exception ex) {
        rep.setStatus(ErrorCodeEnum.METHOD_BOT_ALLOWED.getCode());
        return ResultUtil.error(ErrorCodeEnum.METHOD_BOT_ALLOWED.getCode(), ex.getMessage());
    }

    /**
     * 406相关异常
     *
     * @param rep {@link HttpServletResponse}
     * @param ex  {@link Exception}
     * @return {@link JsonResult}
     */
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public JsonResult request406(HttpServletResponse rep, Exception ex) {
        rep.setStatus(ErrorCodeEnum.NOT_ACCEPTABLE.getCode());
        return ResultUtil.error(ErrorCodeEnum.NOT_ACCEPTABLE.getCode(), ex.getMessage());
    }

    /**
     * 500相关异常
     *
     * @param rep {@link HttpServletResponse}
     * @param ex  {@link Exception}
     * @return {@link JsonResult}
     */
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    public JsonResult server500(HttpServletResponse rep, Exception ex) {
        rep.setStatus(ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode());
        return ResultUtil.error(ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode(), ex.getMessage());
    }



    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResult handler(Exception e) {
        if (e instanceof BindException) {
            //这里捕获实体校验的相关异常
            BindException bindException = (BindException) e;
            BindingResult bindingResult = bindException.getBindingResult();
            //获取所有错误信息拼接成的字符串
            StringBuilder errorMessages = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.append("[").append(error.getDefaultMessage()).append("]");
            }
            return ResultUtil.error(ErrorCodeEnum.PARAM_VALID_ERROR.getCode(), errorMessages.toString());
        } else {
            if (e instanceof TipException || e instanceof IllegalArgumentException) {
                return ResultUtil.error(ErrorCodeEnum.UNKNOWN_ERROR.getCode(), e.toString());
            } else {
                e.printStackTrace();
                return ResultUtil.error(ErrorCodeEnum.UNKNOWN_ERROR.getCode(), "系统繁忙");
            }
        }
    }

    /**
     * 参数校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public JsonResult<?> handler(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return ResultUtil.error(ErrorCodeEnum.PARAM_VALID_ERROR.getCode(), e.getMessage());
    }


}
