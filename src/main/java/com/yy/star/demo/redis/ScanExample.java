package com.yy.star.demo.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

/**
 *  游标迭代器（过滤器）——Scan
 * 小结
 * 通过本文我们可以知道 Scan 包含以下四个指令：
 * <p>
 * Scan：用于检索当前数据库中所有数据；
 * HScan：用于检索哈希类型的数据；
 * SScan：用于检索集合类型中的数据；
 * ZScan：由于检索有序集合中的数据。
 * Scan 具备以下几个特点：
 * <p>
 * Scan 可以实现 keys 的匹配功能；
 * Scan 是通过游标进行查询的不会导致 Redis 假死；
 * Scan 提供了 count 参数，可以规定遍历的数量；
 * Scan 会把游标返回给客户端，用户客户端继续遍历查询；
 * Scan 返回的结果可能会有重复数据，需要客户端去重；
 * 单次返回空值且游标不为 0，说明遍历还没结束；
 * Scan 可以保证在开始检索之前，被删除的元素一定不会被查询出来；
 * 在迭代过程中如果有元素被修改， Scan 不保证能查询出相关的元素。
 */
public class ScanExample {
    public static void main(String[] args) {
        // 添加十万条数据
//        initData();
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("111111");
        // 定义match和count参数
        ScanParams params = new ScanParams();
        params.count(10000);
        params.match("user_tocken_9999*");
        // 游标
        String cursor = "0";
        while (true) {
            ScanResult<String> res = jedis.scan(cursor, params);
            if (res.getCursor().equals("0")) {
                // 表示最后一条
                break;
            }
            cursor = res.getCursor();// 设置游标
            for (String item : res.getResult()) {
                // 打印查询结果
                System.out.println("查询结果: " + item);
            }
        }
    }

    public static void initData() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("111111");
        Pipeline pipe = jedis.pipelined();
        for (int i = 1; i < 100001; i++) {
            pipe.set("user_tocken_" + i, "id" + i);
        }
        // 执行命令
        pipe.sync();
        System.out.println("数据插入完成");

    }
}
