package com.yy.star.demo.java8;

import java.util.concurrent.ForkJoinPool;

/**
 * @program: star
 * @description:
 * @author: yang
 * @create: 2023-07-28 11:23
 **/
public class ForkJoinPoolTest {

    private static int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public static void main(String[] args) {
        System.out.println("result=>" + calc());
        AccumuratorRecursiveTask task = new AccumuratorRecursiveTask(0, data.length, data);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Integer result = forkJoinPool.invoke(task);
        System.out.println("AccumuratorRecursiveTask=>" + result);
        AccumuratorRecursiveAction action = new AccumuratorRecursiveAction(0, data.length, data);
        forkJoinPool.invoke(action);
        System.out.println("AccumuratorRecursiveAction=>" + AccumuratorRecursiveAction.AccumulatorHelper.getResult());
    }

    private static int calc() {
        int result = 0;
        for (int i = 0; i < data.length; i++) {
            result += data[i];
        }
        return result;
    }
}
