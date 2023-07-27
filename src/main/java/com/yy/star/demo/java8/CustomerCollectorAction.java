package com.yy.star.demo.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;

/**
 * @program: star
 * @description:
 * @author: yang
 * @create: 2023-07-27 10:16
 **/
public class CustomerCollectorAction {

    public static void main(String[] args) {
        Collector<String, List<String>, List<String>> collector = new ToListCollector();
        String[] arrs = new String[]{"Alex", "Wang", "Hello", "Lambda", "Collector", "Java"};
        Arrays.asList("Alex", "Wang", "Hello", "Lambda", "Collector", "Java");
        List<String> result = Arrays.stream(arrs).filter(s -> s.length() > 5).collect(collector);
        System.out.println(result);
        int maxThreadCount = Runtime.getRuntime().availableProcessors();
        System.out.println("Max thread count: " + maxThreadCount);
    }
}
