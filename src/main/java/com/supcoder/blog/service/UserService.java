package com.supcoder.blog.service;

import com.supcoder.blog.model.domain.User;

/**
 * User Service 接口
 *
 * @author zbw
 * @since 2017/7/12 21:25
 */
public interface UserService {

    /**
     * 用户登陆
     *
     * @param username 用户名
     * @param password 密码
     * @return User
     */
    User login(String username, String password);

    /**
     * 用户重置密码
     *
     * @param username    用户名
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return boolean
     */
    boolean reset(String username, String oldPassword, String newPassword);
}
