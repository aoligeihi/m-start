package com.yy.star.demo.java8;

import java.util.concurrent.RecursiveTask;

/**
 * @program: star
 * @description:
 * @author: yang
 * @create: 2023-07-28 11:28
 **/
public class AccumuratorRecursiveTask extends RecursiveTask<Integer> {
    private final int start;

    private final int end;

    private final int[] data;

    private final int LIMIT = 3;

    public AccumuratorRecursiveTask(int start, int end, int[] data) {
        this.start = start;
        this.end = end;
        this.data = data;
    }

    @Override
    protected Integer compute() {
        if ((end - start) <= LIMIT) {
            int result = 0;
            for (int i = start; i < end; i++) {
                result += data[i];
            }
            return result;
        }

        int mid = (start + end) / 2;
        AccumuratorRecursiveTask left = new AccumuratorRecursiveTask(start, mid, data);
        AccumuratorRecursiveTask right = new AccumuratorRecursiveTask(mid, end, data);
        left.fork();

        Integer rightResult = right.compute();
        Integer leftResult = left.join();

        return rightResult + leftResult;
    }
}
