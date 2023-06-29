package com.yy.star.demo.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * 小结
 * 事务为多个命令提供一次性按顺序执行的机制，与 Redis 事务相关的命令有以下五个：
 *
 * multi：开启事务
 * exec：执行事务
 * discard：丢弃事务
 * watch：为事务提供乐观锁实现
 * unwatch：取消监控（取消事务中的乐观锁）
 * 正常情况下 Redis 事务分为三个阶段：
 * 开启事务、命令入列、执行事务。Redis 事务并不支持运行时错误的事务回滚，但在某些入列错误，
 * 如 set key 或者是 watch 监控项被修改时，提供整个事务回滚的功能。
 */
public class TransactionExample {
    public static void main(String[] args) {
        // 创建redis连接
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 设置redis
        jedis.auth("111111");
        // 设置键值
        jedis.set("k","v");
        // 开启监视 watch
        jedis.watch("k");
        // 开启事务
        Transaction tx = jedis.multi();
        // 命令入列
        tx.set("k","v2");
        // 执行事务
        tx.exec();
        System.out.println(jedis.get("k"));
        jedis.close();
    }
}
