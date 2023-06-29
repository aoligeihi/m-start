package com.yy.star.demo.redis;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * 集合类型 (Set) 是一个无序并唯一的键值集合。
 * 集合类型和列表类型的区别如下：
 *
 * 列表可以存储重复元素，集合只能存储非重复元素；
 * 列表是按照元素的先后顺序存储元素的，而集合则是无序方式存储元素的。
 *
 *  使用场景
 * 集合类型的经典使用场景如下：
 *
 * 微博关注我的人和我关注的人都适合用集合存储，可以保证人员不会重复；
 * 中奖人信息也适合用集合类型存储，这样可以保证一个人不会重复中奖。
 */
public class SetExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 密码
        jedis.auth("111111");
        // 创建集合添加元素
        jedis.sadd("set1","java","golang");
        // 查询集合中的所有元素
        Set<String> members = jedis.smembers("set1");
        System.out.println(members);// 输出: [java,golang]
        // 查询集合中的元素数量
        System.out.println(jedis.scard("set1"));
        // 移除集合中的一个元素
        jedis.srem("set1","golang");
        System.out.println(jedis.smembers("set1")); // 输出：[java]
        // 创建集合set2 并添加元素
        jedis.sadd("set2","java","golang");
        // 查询两个集合中交集
        Set<String> inters = jedis.sinter("set1", "aset2");
        System.out.println(inters);// 输出:[java]
        // 查询两个集合的并集
        Set<String> unions = jedis.sunion("set1", "set2");
        System.out.println(unions);// 输出: [java,golang]
        // 查询两个集合的错集
        Set<String> diffs = jedis.sdiff("set2", "set1");
        System.out.println(diffs);// 输出:[golang]
    }
}
