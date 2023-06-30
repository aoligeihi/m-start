package com.yy.star.demo.redis;

import redis.clients.jedis.Jedis;

import java.time.Instant;
import java.util.List;

/**
 * 延迟队列
 * 延迟队列的使用场景
 * 1.超过 30 分钟未支付的订单，将会被取消
 * 2.外卖商家超过 5 分钟未接单的订单，将会被取消
 * 3.在平台注册但 30 天内未登录的用户，发短信提醒
 *
 * 常见实现方式
 * Redis 延迟队列实现的思路、优点：目前市面上延迟队列的实现方式基本分为三类，
 * 第一类是通过程序的方式实现，例如 JDK 自带的延迟队列 DelayQueue，
 * 第二类是通过 MQ 框架来实现，例如 RabbitMQ 可以通过 rabbitmq-delayed-message-exchange 插件来实现延迟队列，
 * 第三类就是通过 Redis 的方式来实现延迟队列。
 *
 * Redis 实现方式
 * Redis 是通过有序集合（ZSet）的方式来实现延迟消息队列的，ZSet 有一个 Score 属性可以用来存储延迟执行的时间。
 * 优点:
 * 1.灵活方便，Redis 是互联网公司的标配，无序额外搭建相关环境；
 * 2.可进行消息持久化，大大提高了延迟队列的可靠性；
 * 3.分布式支持，不像 JDK 自身的 DelayQueue；
 * 4.高可用性，利用 Redis 本身高可用方案，增加了系统健壮性。
 * 缺点:
 * 1.需要使用无限循环的方式来执行任务检查，会消耗少量的系统资源。
 *
 *
 * 本文我们介绍了延迟队列的使用场景以及各种实现方案，
 * 其中 Redis 的方式是最符合我们需求的，它主要是利用有序集合的 score 属性来存储延迟执行时间，再开启一个无限循环来判断是否有符合要求的任务，如果有的话执行相关逻辑，没有的话继续循环检测。
 */
public class DelayQueueExample {
    // zset key
    private static final String _KEY = "myDelayQueue";

    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("111111");
        // 延迟30s执行(30s后的时间)
        long delayTime = Instant.now().plusSeconds(30).getEpochSecond();
        jedis.zadd(_KEY, delayTime, "order_1");
        // 继续添加测试数据
        jedis.zadd(_KEY, Instant.now().plusSeconds(2).getEpochSecond(), "order_2");
        jedis.zadd(_KEY, Instant.now().plusSeconds(2).getEpochSecond(), "order_3");
        jedis.zadd(_KEY, Instant.now().plusSeconds(7).getEpochSecond(), "order_4");
        jedis.zadd(_KEY, Instant.now().plusSeconds(10).getEpochSecond(), "order_5");
        // 开启延迟队列
        doDelayQueue(jedis);
    }

    /**
     * 延迟队列消费
     *
     * @param jedis Redis 客户端
     */
    public static void doDelayQueue(Jedis jedis) throws InterruptedException {
        while (true) {
            // 当前时间
            Instant nowInstant = Instant.now();
            // .getEpochSecond() 这个方法是 Instant 类的一个实例方法，它返回一个 long 值，表示这个时间点距离 1970-01-01T00:00:00Z 的秒数。 这个值也被称为 Unix 时间戳。
            long lastSecond = nowInstant.plusSeconds(-1).getEpochSecond();
            long nowSecond = nowInstant.getEpochSecond();
            // 查询当前时间的所有任务
            List<String> data = jedis.zrangeByScore(_KEY, lastSecond, nowSecond);
            for (String item : data) {
                // 消费任务
                System.out.println("消费: " + item);
            }
            // 删除已执行的任务
            jedis.zremrangeByScore(_KEY, lastSecond, nowSecond);
            Thread.sleep(1000);// 每秒轮询一次
        }
    }

}
