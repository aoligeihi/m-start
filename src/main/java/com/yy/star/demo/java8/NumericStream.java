package com.yy.star.demo.java8;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NumericStream {
    public static void main(String[] args) {
        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});

        IntStream intStream = stream.mapToInt(i -> i.intValue());

        int result = intStream.filter(i -> i > 3).sum();

        // int(4byte/32bit)
        System.out.println(result);

    }
}
