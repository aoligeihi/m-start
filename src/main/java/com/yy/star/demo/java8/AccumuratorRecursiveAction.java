package com.yy.star.demo.java8;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: star
 * @description:
 * @author: yang
 * @create: 2023-07-28 11:41
 **/
public class AccumuratorRecursiveAction extends RecursiveAction {

    private final int start;

    private final int end;

    private final int[] data;

    private final int LIMIT = 3;

    public AccumuratorRecursiveAction(int start, int end, int[] data) {
        this.start = start;
        this.end = end;
        this.data = data;
    }

    @Override
    protected void compute() {
        if ((end - start) <= LIMIT) {
            for (int i = start; i < end; i++) {
                AccumulatorHelper.accumulate(data[i]);
            }
        } else {
            int mid = (start + end) / 2;
            AccumuratorRecursiveAction left = new AccumuratorRecursiveAction(start, mid, data);
            AccumuratorRecursiveAction right = new AccumuratorRecursiveAction(mid, end, data);
            left.fork();
            right.fork();
            left.join();
            right.join();
        }
    }

    static class AccumulatorHelper {

        private static final AtomicInteger result = new AtomicInteger(0);

        static void accumulate(int value) {
            result.getAndAdd(value);
        }

        static int getResult() {
            return result.get();
        }

        static void rest() {
            result.set(0);
        }
    }
}
