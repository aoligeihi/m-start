package com.yy.star.demo.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.args.ListPosition;

import java.util.List;

/**
 * 列表类型 (List) 是一个使用链表结构存储的有序结构，它的元素插入会按照先后顺序存储到链表结构中，
 * 因此它的元素操作 (插入\删除) 时间复杂度为 O(1)，
 * 所以相对来说速度还是比较快的，但它的查询时间复杂度为 O(n)，因此查询可能会比较慢
 */
public class ListExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 声明 Redis key
        final String REDISKEY = "list";
        // 在头部出入一个或多个元素
        long lpushResult = jedis.lpush(REDISKEY, "Java", "Sql");
        System.out.println(lpushResult);// 输出: 2
        // 获取第0个元素的值
        String idValue = jedis.lindex(REDISKEY, 0);
        System.out.println(idValue); // 输出: Sql
        // 查询指定区间的元素
        List<String> list = jedis.lrange(REDISKEY, 0, -1);
        System.out.println(list);// 输出:[Sql,Java]
        // 在元素java前面田间mydsql元素
        jedis.linsert(REDISKEY, ListPosition.BEFORE, "Java", "Mysql");
        System.out.println(jedis.lrange(REDISKEY, 0, -1));// 输出:[Sql,MySql,Java]
        jedis.close();
    }
}
