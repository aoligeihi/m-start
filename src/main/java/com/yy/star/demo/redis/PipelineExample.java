package com.yy.star.demo.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * Redis 管道技术——Pipeline
 *
 * 管道技术需要注意的事项
 * 管道技术虽然有它的优势，但在使用时还需注意以下几个细节：
 *
 * 发送的命令数量不会被限制，但输入缓存区也就是命令的最大存储体积为 1GB，当发送的命令超过此限制时，命令不会被执行，并且会被 Redis 服务器端断开此链接；
 * 如果管道的数据过多可能会导致客户端的等待时间过长，导致网络阻塞；
 * 部分客户端自己本身也有缓存区大小的设置，如果管道命令没有没执行或者是执行不完整，可以排查此情况或较少管道内的命令重新尝试执行。
 * 小结
 * 使用管道技术可以解决多个命令执行时的网络等待，它是把多个命令整合到一起发送给服务器端处理之后统一返回给客户端，这样就免去了每条命令执行后都要等待的情况，从而有效地提高了程序的执行效率，
 * 但使用管道技术也要注意避免发送的命令过大，或管道内的数据太多而导致的网络阻塞。
 */
public class PipelineExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 密码
        jedis.auth("111111");
        // 记录执行开始时间
        long beginTime = System.currentTimeMillis();
        // 获取Pipeline对象
        Pipeline pipe = jedis.pipelined();
        // 设置多个redis命令
        for (int i = 0; i < 100; i++) {
            pipe.set("key" + i, "val" + i);
            pipe.del("key" + i);
        }
        // 执行命令
        // pipe.sync();
        // 执行命令并返回结果
        List<Object> res = pipe.syncAndReturnAll();
        for (Object obj : res) {
            // 打印结果
            System.out.println(obj);
        }
    }
}
