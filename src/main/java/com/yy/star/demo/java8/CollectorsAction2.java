package com.yy.star.demo.java8;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import static com.yy.star.demo.java8.CollectorsAction.menu;

/**
 * @program: star
 * @description:
 * @author: yang
 * @create: 2023-07-26 15:32
 **/
public class CollectorsAction2 {

    public static void main(String[] args) {
        testgroupingByConcurrentWithFunction();
        testgroupingByConcurrentWithFunctionAndCollector();
        testgroupingByConcurrentWithFunctionAndSupplierAndCollector();
        testJoiningWithDelimiter();
        testJoiningWithDelimiterAndPrefixAndSuffix();
        testMapping();
    }

    private static void testgroupingByConcurrentWithFunction() {
        System.out.println("testgroupingByConcurrentWithFunction");
        ConcurrentMap<Integer, List<Dish>> collect = menu.stream().collect(Collectors.groupingByConcurrent(Dish::getCalories));
        Optional.of(collect.getClass()).ifPresent(System.out::println);
        Optional.of(collect).ifPresent(System.out::println);
    }

    private static void testgroupingByConcurrentWithFunctionAndCollector() {
        System.out.println("testgroupingByConcurrentWithFunctionAndCollector");
        ConcurrentMap<Dish.Type, Double> collect = menu.stream().collect(Collectors.groupingByConcurrent(Dish::getType, Collectors.averagingInt(Dish::getCalories)));
        Optional.of(collect.getClass()).ifPresent(System.out::println);
        Optional.of(collect).ifPresent(System.out::println);
    }

    private static void testgroupingByConcurrentWithFunctionAndSupplierAndCollector() {
        System.out.println("testgroupingByConcurrentWithFunctionAndSupplierAndCollector");
        ConcurrentMap<Dish.Type, Double> collect = menu.stream().collect(Collectors.groupingByConcurrent(Dish::getType, ConcurrentSkipListMap::new, Collectors.averagingInt(Dish::getCalories)));
        Optional.of(collect.getClass()).ifPresent(System.out::println);
        Optional.of(collect).ifPresent(System.out::println);
    }

    /**
     * @Description:
     * @Param: []
     * @return: void
     * @Author: yang
     * @Date: 2023/7/26
     */
    private static void testJoining() {
        System.out.println("testJoining");
        Optional.of(menu.stream().map(Dish::getName).collect(Collectors.joining())).ifPresent(System.out::println);
    }

    private static void testJoiningWithDelimiter() {
        System.out.println("testJoiningWithDelimiter");
        Optional.of(menu.stream().map(Dish::getName).collect(Collectors.joining(","))).ifPresent(System.out::println);
    }

    private static void testJoiningWithDelimiterAndPrefixAndSuffix() {
        System.out.println("testJoiningWithDelimiter");
        Optional.of(menu.stream().map(Dish::getName).collect(Collectors.joining(",", "Names[", "]"))).ifPresent(System.out::println);
    }

    private static void testMapping() {
        System.out.println("testMapping");
        Optional.of(menu.stream().collect(Collectors.mapping(Dish::getName, Collectors.joining(",")))).ifPresent(System.out::println);
    }

    private static void testMaxBy() {
        System.out.println("testMaxBy");
        menu.stream().collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))).ifPresent(System.out::println);
    }

    private static void testMinBy() {
        System.out.println("testMinBy");
        menu.stream().collect(Collectors.minBy(Comparator.comparingInt(Dish::getCalories))).ifPresent(System.out::println);
    }
}

