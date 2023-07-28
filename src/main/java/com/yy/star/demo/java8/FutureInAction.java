package com.yy.star.demo.java8;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @program: star
 * @description:
 * @author: yang
 * @create: 2023-07-28 15:05
 **/
public class FutureInAction {
    public static void main(String[] args) throws InterruptedException {
        Future<String> future = invoke(() -> {
            try {
                Thread.sleep(10000);
                return "I am finished";
            } catch (InterruptedException e) {
                return "Error";
            }
        });
        System.out.println(future.get());
        System.out.println(future.get());
        System.out.println(future.get());

        while (!future.isDone()) {
            Thread.sleep(10);
        }
        System.out.println(future.get());

        // 最初始的写法
        block(() -> {
            try {
                Thread.sleep(10000);
                return "I am finished";
            } catch (InterruptedException e) {
                return "Error";
            }
        });
    }

    /**
     * 最初始的方法演示
     *
     * @param callable
     * @param <T>
     * @return
     */
    private static <T> T block(Callable<T> callable) {
        return callable.action();
    }

    private static <T> Future<T> invoke(Callable<T> callable) {
        AtomicReference<T> result = new AtomicReference<>();
        AtomicBoolean finshed = new AtomicBoolean(false);
        Thread t = new Thread(() -> {
            T value = callable.action();
            result.set(value);
            finshed.set(true);
        });
        t.start();

        Future<T> future = new Future<T>() {
            @Override
            public T get() {
                return result.get();
            }

            @Override
            public boolean isDone() {
                return finshed.get();
            }
        };
        return future;

    }

    private interface Future<T> {

        T get();

        boolean isDone();
    }

    private interface Callable<T> {
        T action();
    }

}
