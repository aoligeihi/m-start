package com.yy.star.demo.java8;

import java.util.concurrent.*;

/**
 * @program: star
 * @description:
 * @author: yang
 * @create: 2023-07-28 15:23
 **/
public class FutureInAction2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> {
            try {
                Thread.sleep(10000L);
                return "I am finished.";
            } catch (InterruptedException e) {
                return "I am Error";
            }
        });
//        String value = future.get(10, TimeUnit.MILLISECONDS);
        while (!future.isDone()) {
            Thread.sleep(10);
        }
//        System.out.println(value);
        System.out.println(future.get());
//        executorService.shutdownNow();
        executorService.shutdown();
    }


}
