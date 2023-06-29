package com.yy.star.demo.redis;

import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.args.GeoUnit;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查找附近的人 ego
 * 应用场景
 * Redis 中的 GEO 经典使用场景如下：
 * <p>
 * 查询附近的人、附近的地点等；
 * 计算相关的距离信息。
 * 小结
 * GEO 是 Redis 3.2 版本中新增的功能，只有升级到 3.2+ 才能使用，GEO 本质上是基于 ZSet 实现的，这点在 Redis 源码找到相关信息，
 * 我们可以 GEO 使用实现查找附近的人或者附近的地点，还可以用它来计算两个位置相隔的直线距离。
 */
public class GeoHashExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("111111");
        Map<String, GeoCoordinate> map = new HashMap<>();
        // 添加小明的位置
        map.put("xiaoming", new GeoCoordinate(116.404269, 39.913164));
        // 添加小红的位置
        map.put("xiaohong", new GeoCoordinate(116.36, 39.922461));
        // 添加小美的位置
        map.put("xiaomei", new GeoCoordinate(116.499705, 39.874635));
        // 添加小二
        map.put("xiaoer", new GeoCoordinate(116.193275, 39.996348));
        jedis.geoadd("person", map);
        // 查询小明和小红的直线距离
        System.out.println("小明和小红相距：" + jedis.geodist("person", "xiaoming",
                "xiaohong", GeoUnit.KM) + " KM");
        // 查询小明附近 5 公里的人
        List<GeoRadiusResponse> res = jedis.georadiusByMemberReadonly("person", "xiaoming",
                5, GeoUnit.KM);
        for (int i = 1; i < res.size(); i++) {
            System.out.println("小明附近的人：" + res.get(i).getMemberByString());
        }
    }
}
