package com.yy.star.interceptors;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.yy.star.utils.JWTUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;

public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        // 获取请求头中的令牌
//        String token = request.getHeader("token");
        String authorizationHeader = request.getHeader("authorization");
        String token = "";
        if (StringUtils.isNotBlank(authorizationHeader)) {
            token = authorizationHeader.substring(7);
        }

        try {
            JWTUtils.verify(token);
            return true;// 放行请求
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("msg", "无效签名!");
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            map.put("msg", "token过期!");

        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            map.put("msg", "token算法不一致!");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "token无效!!!!");
        }
        map.put("state", false);// 设置状态
        // 将map转换位JSON
        JSONObject jsonObject = new JSONObject(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonObject);
        return false;
    }

}
