package com.supcoder.blog.controller.manage;

import com.supcoder.blog.controller.BaseController;
import com.supcoder.blog.exception.ErrorCodeEnum;
import com.supcoder.blog.model.domain.User;
import com.supcoder.blog.service.UserService;
import com.supcoder.blog.util.FameConsts;
import com.supcoder.blog.util.JsonResult;
import com.supcoder.blog.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 后台用户验证 Controller
 *
 * @author zbw
 * @since 2017/7/11 20:15
 */
@RestController
@RequestMapping("/api/manage")
public class AuthController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 后台登录
     *
     * @param response   {@link HttpServletResponse}
     * @param username   用户名
     * @param password   密码
     * @param rememberMe 是否记住
     * @return {@see ResultUtil.success()}
     */
    @PostMapping("login")
    @ResponseBody
    public JsonResult login(HttpServletResponse response, @RequestParam String username, @RequestParam String password, String rememberMe) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return ResultUtil.error("用户名和密码不能为空");
        }
        User user = userService.login(username, password);
        request.getSession().setAttribute(FameConsts.USER_SESSION_KEY, user);

        return ResultUtil.success();
    }

    /**
     * 登出
     *
     * @return {@see ResultUtil.success()}
     */
    @PostMapping("logout")
    @ResponseBody
    public JsonResult logout() {
        User user = this.user();
        if (null == user) {
            return ResultUtil.error("用户没有登陆");
        }

        request.getSession().removeAttribute(FameConsts.USER_SESSION_KEY);
        return ResultUtil.success();
    }

    /**
     * 重置密码
     *
     * @param username    用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return {@see Boolean}
     */
    @PostMapping("reset")
    @ResponseBody
    public JsonResult resetPassword(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
        if (!username.equals(this.user().getUsername())) {
            return ResultUtil.error("用户名与登陆的不符合");
        }

        boolean result = userService.reset(username, oldPassword, newPassword);
        return ResultUtil.success(result);
    }

    /**
     * 获取用户名
     *
     * @return {@see String}
     */
    @GetMapping("username")
    @ResponseBody
    public JsonResult username() {
        User user = this.user();
        if (null == user) {
            return ResultUtil.error(ErrorCodeEnum.NOT_LOGIN.getCode(),ErrorCodeEnum.NOT_LOGIN.getMsg());
        }

        return ResultUtil.success(user.getUsername());
    }

    /**
     * 获取用户名
     *
     * @return {@see String}
     */
    @GetMapping("userInfo")
    @ResponseBody
    public JsonResult userInfo() {
        User user = this.user();
        if (null == user) {
            return ResultUtil.error(ErrorCodeEnum.NOT_LOGIN.getCode(),ErrorCodeEnum.NOT_LOGIN.getMsg());
        }
        user.setPasswordMd5("");
        return ResultUtil.success(user);
    }


}
