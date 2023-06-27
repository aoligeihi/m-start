package com.yy.star.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author MI
 * @ClassName ThreadPoolConfig.java
 * @createTime 2021年08月23日 14:12:00
 */
@Configuration
public class ThreadPoolConfig {

    @Bean(name = "threadPool")
    public ExecutorService threadPool() {
        ThreadFactory nameThreadFactory = new ThreadFactoryBuilder().setNameFormat("sys-bus-thread-%d").build();
        int processors = Runtime.getRuntime().availableProcessors();
        return new ThreadPoolExecutor(
                processors / 2, processors, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                nameThreadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
