package com.yy.star.demo.redis;

import redis.clients.jedis.Jedis;

/**
 * 优秀的基数统计算法——HyperLogLog
 */
public class HyperLogLogExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("111111");
        // 添加元素
        jedis.pfadd("k", "redis", "sql");
        jedis.pfadd("k", "redis");
        // 统计元素
        long count = jedis.pfcount("k");
        // 打印统计元素
        System.out.println("k: " + count);
        // 合并HLL
        jedis.pfmerge("k1", "k");
        // 打印新 HLL
        System.out.println("K2:" + jedis.pfcount("k2"));
    }
}
