package com.yy.star.demo.redis;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * 可以使用 object encoding key 命令来查看对象(键值对)存储的数据类型，当使用此命令来查询 SDS 对象时，发现 SDS 对象竟然包含了三种不同的数据类型：int、embstr 和 raw。
 * int 整数类型对应的就是 int 类型，字符串则对应是 embstr 类型，当字符串长度大于 44 字节时，会变为 raw 类型存储。
 * 在 Redis 中，SDS 的存储值大于 64 字节时，Redis 的内存分配器会认为此对象为大字符串，并使用 raw 类型来存储，
 * 当数据小于 64 字节时(字符串类型)，会使用 embstr 类型存储。
 * ?既然内存分配器的判断标准是 64 字节，那为什么 embstr 类型和 raw 类型的存储判断值是 44 字节？
 *
 * 这是因为 Redis 在存储对象时，会创建此对象的关联信息，redisObject 对象头和 SDS 自身属性信息，这些信息都会占用一定的存储空间，因此长度判断标准就从 64 字节变成了 44 字节。
 */
public class StringExample {
    public static void main(String[] args) {
        // ip 端口号
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 密码
        jedis.auth("111111");// 密码
        // 添加一个元素
        jedis.set("mystr", "redis");
        // 获取元素
        String mystr = jedis.get("mystr");
        System.out.println(mystr);// 输出redis
        // 添加多个元素
        jedis.mset("db", "redis", "lang", "java");
        // 获取多个元素
        List<String> mlist = jedis.mget("db", "lang");
        System.out.println(mlist);  // 输出：[redis, java]
        // 给元素追加字符串
        jedis.append("db", ",mysql");
        System.out.println(jedis.get("db"));// 输出: redis,mysql
        // 当key不存在时,赋值键值
        long setnx = jedis.setnx("db", "db2");
        // 因为db元素已经存在,所以返回0条修改
        System.out.println(setnx);// 输出 0
        // 字符串截取
        String range = jedis.getrange("db", 0, 2);
        System.out.println(range);// 输出: red
        // 添加键值并设置过期时间(单位:毫秒)
        String setex = jedis.setex("db", 10, "redis");
        System.out.println(setex);// 输出ok
        // 查询键值的过期时间
        long ttl = jedis.ttl("db");
        System.out.println(ttl); // 输出: 1000

        // 储存用户信息
        DemoUser user = new DemoUser();
        user.setId("1");
        user.setName("Redis");
        user.setAge(10);
        String jsonUser = JSON.toJSONString(user);
        // 打印用户信息json
        System.out.println(jsonUser);// 输出:{"id":"1","name":"Redis","age":10}
        // 字符串存入 Redius
        jedis.set("user", jsonUser);
        // 使用用户信息 从redis反序列化出来
        String getUserData = jedis.get("user");
        DemoUser userData = JSON.parseObject(getUserData, DemoUser.class);
        // 打印对象属性
        System.out.println(userData.getId() + ":" + userData.getName());// 输出: 1:redis
    }
}
