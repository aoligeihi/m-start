package com.yy.star.demo.java8;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @program: star
 * @description:
 * @author: yang
 * @create: 2023-07-28 10:39
 **/
public class ParallelProcessing {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println("The best process time(normalAdd)=>" + measureSumPerFormance(ParallelProcessing::normalAdd, 10_000_000) + " MS");
        System.out.println("The best process time(iterateStream)=>" + measureSumPerFormance(ParallelProcessing::iterateStream, 10_000_000) + " MS");
        System.out.println("The best process time(paraleelStream)=>" + measureSumPerFormance(ParallelProcessing::paraleelStream, 10_000_000) + " MS");
        System.out.println("The best process time(paraleelStream2)=>" + measureSumPerFormance(ParallelProcessing::paraleelStream2, 10_000_000) + " MS");
        System.out.println("The best process time(paraleelStream3)=>" + measureSumPerFormance(ParallelProcessing::paraleelStream3, 100_000_000) + " MS");
    }

    private static long measureSumPerFormance(Function<Long, Long> adder, long limit) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long startTimestamp = System.currentTimeMillis();
            Long result = adder.apply(limit);
            long duration = System.currentTimeMillis() - startTimestamp;
            System.out.println("The result of sum=>" + result);
            if (duration < fastest) {
                fastest = duration;
            }
        }
        return fastest;
    }

    private static long iterateStream(long limit) {
        return Stream.iterate(1L, i -> i + 1).limit(limit).reduce(0L, Long::sum);
    }

    private static long paraleelStream(long limit) {
        return Stream.iterate(1L, i -> i + 1).parallel()
                .limit(limit).reduce(0L, Long::sum);
    }

    private static long paraleelStream2(long limit) {
        return Stream.iterate(1L, i -> i + 1).mapToLong(Long::longValue).parallel()
                .limit(limit).reduce(0L, Long::sum);
    }

    private static long paraleelStream3(long limit) {
        return LongStream.rangeClosed(1, limit).parallel().reduce(0L, Long::sum);
    }

    private static long normalAdd(long limit) {
        long result = 0L;
        for (long i = 1L; i < limit; i++) {
            result += i;
        }
        return result;
    }
}
