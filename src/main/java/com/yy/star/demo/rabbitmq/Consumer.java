package com.yy.star.demo.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: star
 * @description:
 * @author: yang
 * @create: 2024-02-22 15:14
 **/
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 服务地址
        factory.setHost("47.94.172.192");
        // 账号
        factory.setUsername("guest");
        // 密码
        factory.setPassword("guest");
        // 端口号
        factory.setPort(5672);
        // 创建链接
        Connection connection = factory.newConnection();
        // 创建信道
        Channel channel = connection.createChannel();

        // 接收消息的回调
        DeliverCallback deliverCallback = (consumerTage, message) -> {
            System.out.println("接收到消息" + new String(message.getBody()));
        };

        // 取消消息的回调
        CancelCallback cancelCallback = consumerTage -> {
            System.out.println("消费消息被中断");
        };
        /**
         * 消费消息
         * 1.消费哪个队列.
         * 2.消费成功之后是否需要自动应答. true:自动应答
         * 3.接收消息的回调函数.
         * 4.取消消息的回回调函数.
         */
        channel.basicConsume("yang_demo_queue", true, deliverCallback, cancelCallback);
    }
}
