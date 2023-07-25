package com.yy.star.controller;

import com.yy.star.entity.User;
import com.yy.star.service.UserService;
import com.yy.star.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/login")
    public Map<String, Object> login(User user) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            User userDB = userService.login(user);
            Map<String,String> payload = new HashMap();
            payload.put("id",userDB.getId());
            payload.put("username",userDB.getUsername());
            // 生成JWT令牌
            String token = JWTUtils.getToken(payload);
            map.put("state",true);
            map.put("msg","认证成功");
            map.put("token",token);// 相应token
        }catch (Exception e){
            map.put("state",false);
            map.put("msg",e.getMessage());
        }
        return map;
    }

    @PostMapping("/user/test")
    public Map<String,Object> test(String token){
        HashMap<String, Object> map = new HashMap<>();
        return map;
    }
}