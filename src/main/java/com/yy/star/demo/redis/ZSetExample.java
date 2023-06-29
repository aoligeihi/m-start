package com.yy.star.demo.redis;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;

/**
 * 有序集合类型 (Sorted Set) 相比于集合类型多了一个排序属性 score（分值），
 * 对于有序集合 ZSet 来说，每个存储元素相当于有两个值组成的，一个是有序结合的元素值，一个是排序值。有序集合的存储元素值也是不能重复的，但分值是可以重复的。
 *  有序集合使用 ziplist 格式存储必须满足以下两个条件：
 *
 * 有序集合保存的元素个数要小于 128 个；
 * 有序集合保存的所有元素成员的长度都必须小于 64 字节。
 *
 * 使用场景
 * 有序集合的经典使用场景如下：
 *
 * 学生成绩排名
 * 粉丝列表，根据关注的先后时间排序
 *
 * 5 小结
 * 通过本文的学习我们了解到，有序集合具有唯一性和排序的功能，排序功能是借助分值字段 score 实现的，score 字段不仅可以实现排序功能，还可以实现数据的赛选与过滤的功能。
 * 我们还了解到了有序集合是由
 * 压缩列表 (ziplist) 或跳跃列表 (skiplist) 来存储的，当元素个数小于 128 个，并且所有元素的值都小于 64 字节时，有序集合会采取 ziplist 来存储，
 * 反之则会用 skiplist 来存储，其中 skiplist 是从上往下、从前往后进行元素查找的，相比于传统的普通列表，可能会快很多，因为普通列表只能从前往后依次查找。
 *
 */
public class ZSetExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("111111");
        HashMap<String, Double> map = new HashMap<>();
        map.put("小明",80.5d);
        map.put("小红",75d);
        map.put("老王",85d);
        // 为有序集合(ZSet)添加元素
        jedis.zadd("grade",map);
        // 查询分数在80分到100分之间的人(包含80分喝100分)
        List<String> gradeSet = jedis.zrangeByScore("grade", 80, 100);
        System.out.println(gradeSet);// 输出:[小明,老王]
        // 查询小工的排名(排名从0开始)
        System.out.println(jedis.zrank("grade","小明"));// 输出:1
        // 从集合中移除老王
        jedis.zrem("grade","老王");
        // 查有序集合中所有元素(根据排名从小到大)
        List<String> range = jedis.zrange("grade", 0, -1);
        System.out.println(range); // 输出:[小红,小明]
        // 查询有序集合中所有元素(根据score从小到大)
        List<String> rangeByScore = jedis.zrangeByScore("grade", 0, 100);
        System.out.println(rangeByScore);
    }
}
