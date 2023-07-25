package com.yy.star.demo.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JWTTokenDemo {
    public static void main(String[] args) {
        // 当前时间后两小时
        LocalDateTime nowDime = LocalDateTime.now();
        LocalDateTime localDateTime = nowDime.plusSeconds(10);
        String token = JWT.create()
                .withClaim("username", "张三")// payload 设置自定义用户名
                .withExpiresAt(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()))//设置过期时间
                .sign(Algorithm.HMAC256("token!Q2W#E$RW"));// 设置签名 保密复杂
        // 输出令牌
        System.out.println(token);
        verifyJwt();
    }

    public static void verifyJwt(){
        // 创建验证对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("token!Q2W#E$RW")).build();
        DecodedJWT verify = jwtVerifier.verify("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6IuW8oOS4iSIsImV4cCI6MTY5MDE4MzQyM30.vXrXGMe5yc0WQ-WdJg-AM5QH-6BifqgMCrJDgvdS6VI");
        System.out.println("过期时间: "+verify.getExpiresAt());
        System.out.println(verify.getClaim("userId"));
        System.out.println(verify.getClaim("username"));
    }
}
