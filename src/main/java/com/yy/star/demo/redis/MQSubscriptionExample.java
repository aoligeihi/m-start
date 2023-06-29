package com.yy.star.demo.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * 普通订阅模式
 * 发布订阅模式两个缺点:
 * 1.无法持久化保存消息, 如果Redis服务区宕机或重启所有消息消失
 * 2.发布订阅模式是'发后既忘'的工作模式,如果订阅者离线重连后不能消费之前的历史消息
 * 然而这些缺点在 Redis 5.0 添加了 Stream 类型之后会被彻底的解决。
 * <p>
 * 除了以上缺点外，发布订阅模式还有另一个需要注意问题：当消费端有一定的消息积压时，也就是生产者发送的消息，消费者消费不过来时，
 * 如果超过 32M 或者是 60s 内持续保持在 8M 以上，消费端会被强行断开，这个参数是在配置文件中设置的，默认值是 client-output-buffer-limit pubsub 32mb 8mb 60。
 * 小结
 * 本文介绍了消息队列的几个名词，生产者、消费者对应的就是消息的发送者和接收者，也介绍了发布订阅模式的三个命令：
 * <p>
 * subscribe channel 普通订阅
 * publish channel message 消息推送
 * psubscribe pattern 主题订阅
 */
public class MQSubscriptionExample {
    public static void main(String[] args) throws InterruptedException {
        // 创建一个新线程作为消费者
        new Thread(() -> pConsumer()).start();
        // 暂停0.5s等待消费者初始化
        Thread.sleep(500);
        // 生产发送消息
        producer();
    }

    /**
     * 消费者
     */
    public static void consumer() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("111111");
        // 接收并处理消息
        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                //接收消息,业务处理
                System.out.println("频道 " + channel + " 收到消息:" + message);
            }
        }, "channel");
    }

    /**
     * 生产者
     */
    public static void producer() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("111111");
        // 推送消息
        jedis.publish("channel", "Hello, channel");
    }

    /**
     * 主题订阅模式
     * 主题订阅模式的生产者的代码是一样，只有消费者的代码是不同的
     */
    public static void pConsumer() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("111111");
        // 主题订阅
        jedis.psubscribe(new JedisPubSub() {
            @Override
            public void onPMessage(String pattern, String channel, String message) {
                // 接收消息业务处理
                System.out.println(pattern + " 主题 | 频道 " + channel + " 收到消息: " + message);
            }
        }, "channel");
    }

}
