package com.yy.star.demo.redis;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;

/**
 *
 * ZSet 版消息队列
 * ZSet 版消息队列相比于之前的两种方式，List 和发布订阅方式在实现上要复杂一些，但 ZSet 因为多了一个 score（分值）属性，从而使它具备更多的功能，
 * 例如我们可以用它来存储时间戳，以此来实现延迟消息队列等。
 * <p>
 * 它的实现思路和 List 相同也是利用 zadd 和 zrangebyscore 来实现存入和读取
 * 优缺点分析:
 * ZSet优点:
 * 1.支持消息持久化
 * 2.相比list查询更方便,Set 可以利用 score 属性很方便的完成检索，而 List 则需要遍历整个元素才能检索到某个值。
 * ZSet缺点
 * 1.ZSet不能储存相同元素的值,也就是说消息有一条是重复的那么只能插到有序集合厚葬
 * 2.ZSet是根据score值排序的,不能像list一样,按照插入的顺序排序
 * 3.ZSet没有像List的brpop那样的阻塞弹出的功能
 */
public class ZSetMQExample {
    public static void main(String[] args) throws InterruptedException {
        // 消费者
        new Thread(() -> consumer()).start();
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
        HashMap<String, Double> map = new HashMap<>();
        map.put("张三来了", 100D);
        jedis.zadd("mq", map);
        Thread.sleep(60000);
        map.put("王五也来了", 200D);
        jedis.zadd("mq", map);
    }

    /**
     * 消费者
     */
    public static void consumer() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("111111");
        // 消费消息
        while (true) {
            List<String> messages = jedis.zrangeByScore("mq", 0, 200);
            if (messages.size() > 0) {
                String mesage = messages.iterator().next();
                // 接收到了消息
                System.out.println("接收到消息：" + mesage);
                // 删除消息
                jedis.zrem("mq", mesage);
            }
        }
    }
}
