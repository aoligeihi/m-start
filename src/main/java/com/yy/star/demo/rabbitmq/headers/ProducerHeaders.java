package com.yy.star.demo.rabbitmq.headers;


import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @program: star
 * @description:
 * @author: yang
 * @create: 2024-02-22 14:32
 **/
public class ProducerHeaders {

    private static final String exchangeName = "yang_demo_exchange_headers";

    private static final String queueName_1 = "yang_demo_queue_headers_1";


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

        /**
         * 创建交换机
         * 1.交换机名称.
         * 2.交换机类型. direct,topic,fanout和headers.
         * 3.指定交换机是否需要持久化,如果设置为true,那么交换机的元数据需要持久化
         * 4.指定交换机再没有队列绑定时是否需要删除, 设置为false表示为不删除
         * 5.Map<String,Object>类型,用来指定我们交换机其他的一些结构化参数,我们这里直接设置为null
         */
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.HEADERS, true, false, null);

        /**
         * 生成一个队列
         * 1.队列名称
         * 2.队列是否需要持久化, 但是注意,这里的持久化是指队列名称等这些元数据的持久化,不是队列消息的持久化.
         * 3.表示队列是不是私有的,如果私有的,只有创建他的应用程序才可以消费.
         * 4.队列在没有消费者订阅的情况下是否自动删除.
         * 5.队列的一些机构化信息,比如声明死信队列,磁盘队列会用到.
         */
        channel.queueDeclare(queueName_1, true, false, false, null);

        Map<String, Object> headersMap = new HashMap<>();
        headersMap.put("name", "yuyangXX");
        headersMap.put("sex", "男");
        AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties().builder().headers(headersMap);
        // 发送消息
        String massage = "hello rabbitmq";

        /**
         * 发送消息
         * 1.发送到哪个交换机.
         * 2.队列名称.
         * 3.其它参数信息
         * 4.发送消息的消息体.
         */
        channel.basicPublish(exchangeName, "", properties.build(), "any massage".getBytes());
        channel.close();
        connection.close();
        System.out.println("发送消息成功");
    }

}
