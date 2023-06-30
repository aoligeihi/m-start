package com.yy.star.demo.redis;

import io.micrometer.common.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;

/**
 * 什么是分布式锁？
 * 上面说的锁指的是程序级别的锁，例如 Java 语言中的 synchronized 和 ReentrantLock 在单应用中使用不会有任何问题，但如果放到分布式环境下就不适用了，这个时候我们就要使用分布式锁。
 * 怎么实现分布式锁？
 * 分布式锁比较常见的实现方式有三种：
 * Memcached 实现的分布式锁：使用 add 命令，添加成功的情况下，表示创建分布式锁成功。
 * ZooKeeper 实现的分布式锁：使用 ZooKeeper 顺序临时节点来实现分布式锁。
 * Redis 实现的分布式锁。
 * Redis 分布式锁的实现思路是使用 setnx（set if not exists），如果创建成功则表明此锁创建成功，否则代表这个锁已经被占用创建失败。
 * 小结
 * 本文介绍了锁和分布式锁的概念，锁其实就是用来保证同一时刻只有一个程序可以去操作某一个资源，以此来保证并发时程序能正常执行的。
 * 使用 Redis 来实现分布式锁不能使用 setnx 命令，因为它可能会带来死锁的问题，因此我们可以使用 Redis 2.6.12 带来的多参数的 set 命令来申请锁，
 * 但在使用的时候也要注意锁内的业务流程执行的时间，不能大于锁设置的最大超时时间，不然会带来线程安全问题和锁误删的问题。
 */
public class LockExample {
    static final String _LOCKKEY = "REDISLOCK"; // 锁 key
    static final String _FLAGID = "UUID:6379";// 标识(UUID)
    static final Integer _TIMEOUT = 90;       // 最大超出时间

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("111111");
        // 加锁
        boolean lockResult = lock(jedis, _LOCKKEY, _FLAGID, _TIMEOUT);
        // 逻辑业务处理
        if (lockResult) {
            System.out.println(" 加锁成功");
        } else {
            System.out.println("加锁失败");
        }
        // 手动释放锁
        if (unLock(jedis, _LOCKKEY, _FLAGID)) {
            System.out.println("锁释放成功");
        } else {
            System.out.println("锁释放成功");
        }

    }

    /**
     * 加锁
     *
     * @param jedis       Redis客户端
     * @param key         锁名称
     * @param flagId      锁标识(锁值),用于标识锁的归属
     * @param secondsTime 最大超时时间
     * @return
     */
    public static boolean lock(Jedis jedis, String key, String flagId, Integer secondsTime) {
        SetParams params = new SetParams();
        params.ex(secondsTime);
        params.nx();
        String res = jedis.set(key, flagId, params);
        if (StringUtils.isNotBlank(res) && res.equals("OK")) {
            return true;
        }
        return false;
    }

    public static boolean unLock(Jedis jedis, String lockKey, String flagId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(flagId));
        if ("1L".equals(result)) { // 判断执行结果
            return true;
        }
        return false;
    }

}
