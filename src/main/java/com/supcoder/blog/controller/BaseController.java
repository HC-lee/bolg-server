package com.supcoder.blog.controller;



import com.supcoder.blog.exception.ErrorCodeEnum;
import com.supcoder.blog.model.domain.User;
import com.supcoder.blog.util.CacheUtil;
import com.supcoder.blog.util.FameUtil;
import com.supcoder.blog.util.JsonResult;
import com.supcoder.blog.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 13 on 2017/2/21.
 */
public abstract class BaseController {

    public static String THEME = "themes/default";


    /**
     * 主页的页面主题
     * @param viewName
     * @return
     */
    public String render(String viewName) {
        return THEME + "/" + viewName;
    }

    public BaseController title(HttpServletRequest request, String title) {
        request.setAttribute("title", title);
        return this;
    }

    public BaseController keywords(HttpServletRequest request, String keywords) {
        request.setAttribute("keywords", keywords);
        return this;
    }




    public String render_404() {
        return "comm/error_404";
    }


    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected CacheUtil cacheUtil;

    protected User user() {
        return FameUtil.getLoginUser();
    }

    protected JsonResult error404() {
        return ResultUtil.error(ErrorCodeEnum.NOT_FOUND.getCode(), ErrorCodeEnum.NOT_FOUND.getMsg());
    }


}
