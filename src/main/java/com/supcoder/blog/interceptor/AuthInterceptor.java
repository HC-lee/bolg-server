package com.supcoder.blog.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 授权拦截器
 *
 * @author lee
 */
@Aspect
@Component
public class AuthInterceptor {

    private static final Logger sLogger = LoggerFactory.getLogger(AuthInterceptor.class);

    /**
     * 拦截 com.supcoder.blog.controller包下所以方法
     */
    @Pointcut("execution(public * com.supcoder.blog.controller.*.*(..))")
    public void log() {

    }

    /**
     * 前置通知
     *
     * @param joinPoint
     */
    @Before("log()")//log()方法之前
    public void doBefore(JoinPoint joinPoint) {
        //接收请求，记录请求
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        //记录日志
        sLogger.info("url" + request.getRequestURI());
        sLogger.info("method" + request.getMethod());
        sLogger.info("ip" + request.getRemoteAddr());

        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            sLogger.info("name:" + name + ",value:{" + request.getParameter(name) + "}");
        }
    }

    /**
     * 后置通知
     *
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = " log() ")
    public void doAfter(Object ret) {
        //处理完成，返回
        sLogger.info("response" + ret);
    }


}
