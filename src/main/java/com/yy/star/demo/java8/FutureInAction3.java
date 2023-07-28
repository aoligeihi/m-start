package com.yy.star.demo.java8;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @program: star
 * @description:
 * @author: yang
 * @create: 2023-07-28 15:37
 **/
public class FutureInAction3 {

    public static void main(String[] args) {
        Future<String> future = invoke(() -> {
            try {
                Thread.sleep(10000L);
                return "I am finished.";
            } catch (InterruptedException e) {
                return "I am Error";
            }
        });

        future.setComoletable(new Completable<String>() {
            @Override
            public void complete(String s) {
                System.out.println(s);
            }

            @Override
            public void exception(Throwable cause) {
                System.out.println("error");
                cause.printStackTrace();
            }
        });
    }

    private static <T> Future<T> invoke(Callable<T> callable) {
        AtomicReference<T> result = new AtomicReference<>();
        AtomicBoolean finshed = new AtomicBoolean(false);
        Future<T> future = new Future<T>() {
            private Completable<T> completable;

            @Override
            public T get() {
                return result.get();
            }

            @Override
            public boolean isDone() {
                return finshed.get();
            }

            @Override
            public void setComoletable(Completable<T> comoletable) {
                this.completable = comoletable;
            }

            @Override
            public Completable<T> getCompltable() {
                return null;
            }

        };
        Thread t = new Thread(() -> {
            try {
                T value = callable.action();
                result.set(value);
                finshed.set(true);
                if (future.getCompltable() != null) {
                    future.getCompltable().complete(value);
                }
            } catch (Throwable cause) {
                if (future.getCompltable() != null) {
                    future.getCompltable().exception(cause);
                }
            }
        });
        t.start();
        return future;

    }

    private interface Future<T> {

        T get();

        boolean isDone();

        void setComoletable(Completable<T> comoletable);

        Completable<T> getCompltable();

    }

    private interface Callable<T> {
        T action();
    }

    private interface Completable<T> {
        void complete(T t);

        void exception(Throwable cause);
    }

}
