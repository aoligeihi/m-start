package com.yy.star.demo.java8;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class StreamFilter {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 7, 7, 8);

        List<Integer> result = list.stream().filter(i -> 1 % 2 == 0).collect(toList());

        System.out.println(result);

        result = list.stream().distinct().collect(toList());

        System.out.println(result);

        result = list.stream().skip(5).collect(toList());

        System.out.println(result);

        result = list.stream().limit(50).collect(toList());

        System.out.println(result);
    }
}
