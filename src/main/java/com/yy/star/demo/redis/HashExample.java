package com.yy.star.demo.redis;

import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * 字典类型 (Hash) 又被成为散列类型或者是哈希表类型，它是将一个键值 (key) 和一个特殊的“哈希表”关联起来，这个“哈希表”表包含两列数据：字段和值。
 * <p>
 * 使用场景,哈希字典的典型使用场景如下：
 * <p>
 * 商品购物车，购物车非常适合用哈希字典表示，使用人员唯一编号作为字典的 key，value 值可以存储商品的 id 和数量等信息；
 * 存储用户的属性信息，使用人员唯一编号作为字典的 key，value 值为属性字段和对应的值；
 * 存储文章详情页信息等
 * <p>
 * 小结
 * 本文我们学习了字典类型的操作命令和在代码中的使用，也明白了字典类型实际是由数组和链表组成的，
 * 当字典进行扩容或者缩容时会进行渐进式 rehash 操作，渐进式 rehash 是用来保证 Redis 运行效率的，
 * 它的执行流程是同时保留两个哈希表，把旧表中的元素一点一点的移动到新表中，查询的时候会先查询两个哈希表，
 * 当所有元素都移动到新的哈希表之后，就会删除旧的哈希表。
 */
public class HashExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 密码
        jedis.auth("111111");
        // 把key值定义为变量
        final String REDISKEY = "myhash";
        // 插入单个元素
        jedis.hset(REDISKEY, "key1", "value1");
        // 查询单个元素
        Map<String, String> singleMap = jedis.hgetAll(REDISKEY);
        System.out.println(singleMap.get("key1"));// 输出: value1
        // 查询所有元素
        Map<String, String> allMap = jedis.hgetAll(REDISKEY);
        System.out.println(allMap.get("k2")); // 输出val2
        System.out.println(allMap);// 输出: {key1=value1,k1=val1,k2=val2,k3=val3,k4=val4...}
        // 删除单个元素
        long delResult = jedis.hdel(REDISKEY, "key1");
        System.out.println("删除结果: " + delResult);// 输出: 删除结果1
        // 查询单个元素
        System.out.println(jedis.hget(REDISKEY, "key1"));// 输出:返回null
    }
}
