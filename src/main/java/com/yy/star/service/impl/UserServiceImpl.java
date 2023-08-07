package com.yy.star.service.impl;

import com.yy.star.dao.UserDao;
import com.yy.star.entity.User;
import com.yy.star.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @program: star
 * @description: 用户服务类
 * @author: yang
 * @create: 2023-08-07 13:46
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 通过id获取User
     *
     * @param id
     * @return
     */
    @Override
    public User getUserById(Integer id) {
        return userDao.findById(id).orElse(null);
    }

    /**
     * 登录用户名密码验证
     *
     * @param username
     * @param password
     */
    @Override
    public User login(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Example<User> ue = Example.of(user);
        return userDao.findOne(ue).orElse(null);
    }

    /**
     * 创建用户
     *
     * @param user
     * @return
     */
    @Override
    public Integer createUser(User user) {
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        User save = userDao.save(user);
        return save.getId();
    }
}
