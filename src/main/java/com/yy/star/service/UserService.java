package com.yy.star.service;

import com.yy.star.entity.User;

public interface UserService {

    /**
     * 通过id获取User
     *
     * @param id
     * @return
     */
    User getUserById(Integer id);

    /**
     * 登录用户名密码验证
     */
    User login(String username, String password);

    /**
     * 创建用户
     *
     * @param user
     * @return
     */
    String createUser(User user);

}
