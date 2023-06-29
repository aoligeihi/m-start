package com.yy.star.demo.redis;

import redis.clients.jedis.Jedis;

/**
 * 消息队列 List 和 ZSet 的实现方式
 * 消息队列的另外两种实现方式 List 和 ZSet，它们都是利用自身方法，先把数据放到队列里，在使用无限循环读取队列中的消息，以实现消息队列的功能，
 * 相比发布订阅模式本文介绍的这两种方式的优势是支持持久化，
 * 当然它们各自都存在一些问题，所以期待下一课时 Stream 的出现能够解决这些问题。
 * 优缺点:
 * list优点:
 * 1.消息可以被持久化,借助redis的本身持久化(AOF,RDB或者混合持久化),可以有效的保存数据
 * 2.消费者可以积压消息,不会因为客户端消息过多而被强行断开
 * list缺点:
 * 1.消息不能被重复消费,一个消息被消费完就会被伤处
 * 2.没有主题订阅的功能
 */
public class ListMQExample {
    public static void main(String[] args) throws InterruptedException {
        // 消费者
//        new Thread(() -> consumer()).start();
        // 消费者 改良版
        new Thread(() -> bConsumer()).start();
        // 生产者
        producer();
    }

    /**
     * 生产者
     */
    public static void producer() throws InterruptedException {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("111111");
        // 推送消息
        jedis.lpush("mq", "Hello, List.");
        Thread.sleep(1000);
        jedis.lpush("mq", "message 2.");
        Thread.sleep(2000);
        jedis.lpush("mq", "message 3.");
    }

    /**
     * 消费者
     * 我们使用无限循环来获取队列中的数据，这样就可以实时地获取相关信息了，
     * *但这样会带来另一个新的问题，当队列中如果没有数据的情况下，无限循环会一直消耗系统的资源，这时候我们可以使用 brpop 替代 rpop 来完美解决这个问题。
     */
    public static void consumer() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("111111");
        // 消费消息
        while (true) {
            // 获取消息
            String msg = jedis.rpop("mq");
            if (msg != null) {
                //接收到了消息
                System.out.println("接收到消息: " + msg);
            }
        }
    }

    /**
     * 消费者(阻塞版)
     * <p>
     * 使用 brpop 替代 rpop
     * b 是 blocking 的缩写，表示阻塞读
     * 当队列没有数据时，它会进入休眠状态，
     * 当有数据进入队列之后，它才会“苏醒”过来执行读取任务，这样就可以解决 while 循环一直执行消耗系统资源的问题了
     * brpop() 方法的第一个参数是设置超时时间的，设置 0 表示一直阻塞。
     */
    public static void bConsumer() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("111111");
        while (true) {
            // 阻塞读
            for (String item : jedis.brpop(0, "mq")) {
                // 读取相关数据.进行业务处理
                System.out.println(item);
            }
        }
    }

}
