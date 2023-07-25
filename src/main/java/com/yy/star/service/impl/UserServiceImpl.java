package com.yy.star.service.impl;

import com.yy.star.dao.UserDAO;
import com.yy.star.entity.User;
import com.yy.star.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User login(User user) {
        // 从数据库验证当前用户密码
        User userDB = userDAO.login(user);
        if (userDB != null) {
            return userDB;
        }
        throw new RuntimeException("登录失败-~");
    }
}
