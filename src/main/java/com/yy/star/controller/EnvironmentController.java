package com.yy.star.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: star
 * @description: 获取电脑的环境变量
 * @author: yang
 * @create: 2023-07-31 09:58
 **/
@RestController
public class EnvironmentController {

    @Autowired
    private Environment environment;

    @GetMapping("/getEnvironmentVariable")
    public String getEnvironmentVariable() {
        // 通过Environment接口获取电脑环境变量
        String variableValue = environment.getProperty("JAVA_HOME7");
        return "Name: " + variableValue;
    }

}
