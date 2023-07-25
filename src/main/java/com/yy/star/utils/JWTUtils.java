package com.yy.star.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

public class JWTUtils {

    private static final String SING = "!W@QEREIHRG123666";

    /**
     * 生成token header.payload.sing
     */
    public static String getToken(Map<String, String> map) {
        // 当前时间后两小时
        LocalDateTime nowDime = LocalDateTime.now();
        LocalDateTime localDateTime = nowDime.plusSeconds(10);// 默认10s
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        String token = builder.withExpiresAt(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()))//设置过期时间
                .sign(Algorithm.HMAC256(SING));// sign
        return token;
    }

    /**
     * 验证token 合法性
     *
     * @return
     */
    public static DecodedJWT verify(String token) {
       return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }

    /**
     * 获取token信息方法
     */
    public static DecodedJWT getTokenInfo(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        return verify;
    }
}
