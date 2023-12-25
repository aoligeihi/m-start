package com.yy.star.demo.rabbitmq.simple;

import redis.clients.jedis.ConnectionFactory;

/**
 * @program: star
 * @description:
 * @author: yang
 * @create: 2023-08-30 11:07
 **/
public class Consumer {

    public static void main(String[] args) {

        // 所有中间件技术都基于tcp/ip协议基础之上构建新型协议规范,只不过rabbitmq遵循的是amqp
        // IP port

        // 1: 创建连接工程
//        ConnectionFactory connectionFactory = new ConnectionFactory();

        // 2: 创建连接Connection
        // 3: 通过连接获取通道Channel
        // 4: 通过创建交换机,声明队列,绑定关系,路由key.发送消息,接收消息
        // 5:
    }
}
