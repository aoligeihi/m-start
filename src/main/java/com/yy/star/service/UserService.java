package com.yy.star.service;

import com.yy.star.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User login(User user);
}
