package com.yy.star.demo.redis;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamEntryID;
import redis.clients.jedis.params.XReadParams;
import redis.clients.jedis.resps.StreamEntry;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StreamExample {
    public static void main(String[] args) {
        // 消费者
        new Thread(() -> consumer()).start();
        // 生产者
        producer();
    }

    /**
     * 生产者
     */
    public static void producer() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("111111");
        // 推送消息
        Map<String, String> message = new HashMap<>();
        message.put("data", "Hello, Stream.");
        jedis.xadd("mq", StreamEntryID.NEW_ENTRY, message);
    }

    /**
     * 消费者
     */
    public static void consumer() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("111111");
        // 消费消息
        Map.Entry<String, StreamEntryID> entry = new AbstractMap.SimpleEntry<>("mystream", StreamEntryID.UNRECEIVED_ENTRY);
        while (true) {
            // 获取消息
            List<Map.Entry<String, List<StreamEntry>>> messages = jedis.xread(XReadParams.xReadParams().count(1).block(120 * 1000), (Map<String, StreamEntryID>) entry);
            for (Map.Entry<String, List<StreamEntry>> stream : messages) {
                for (StreamEntry message : stream.getValue()) {
                    // 接收到了消息
                    System.out.println("接收到消息：" + message.getFields().get("data"));
                }
            }
        }
    }
}
