package com.yy.star.demo.redis;

import redis.clients.jedis.Jedis;

/**
 * 键值过期操作
 * 本文我们知道了 Redis 中的四种设置过期时间的方式：expire、pexpire、expireat、pexpireat，其中比较常用的是 expire 设置键值 n 秒后过期。
 * 字符串中可以在添加键值的同时设置过期时间，并可以使用 persist 命令移除过期时间。同时我们也知道了过期键在 RDB 写入和 AOF 重写时都不会被记录。
 * 过期键在主从模式下，从库对过期键的处理要完全依靠主库，主库删除过期键之后会发送 del 命令给所有的从库。
 */
public class TTLTest {
    public static void main(String[] args) throws InterruptedException {
        // 创建redis连接
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // redis 密码
        jedis.auth("111111");
        // 储存键值对(默认永久不过期)
        jedis.set("k","v");
        // 查询TTL (过期时间)
        long ttl = jedis.ttl("k");
        // 打印过期日志
        System.out.println("过期时间:"+ttl);
        // 设置100s后过期
        jedis.expire("k",100);
        // 等待1s后执行
        Thread.sleep(1000);
        // 打印过期日志
        System.out.println("执行expire后的TTL="+jedis.ttl("k"));
        // 设置n毫秒后过期
        jedis.pexpire("k",100000);
        // 设置某个时间戳后过期(精确到秒)
        jedis.pexpireAt("k",15743242343242L);
        // 移除过期时间
        jedis.persist("k");
    }
}
