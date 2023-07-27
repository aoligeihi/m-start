package com.yy.star.demo.java8;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import static com.yy.star.demo.java8.CollectorsAction.menu;

/**
 * @program: star
 * @description:
 * @author: yang
 * @create: 2023-07-27 08:58
 **/
public class CollectorsAction4 {
    public static void main(String[] args) {
        testSummingDouble();
        testToCollection();
        testToConcurrentMap();
        testToConcurrentMap();
        testToConcurrentMapWithBinaryOperatorAndSupplier();
        testToList();
        testToSet();
    }

    private static void testSummingDouble() {
        System.out.println("testSummingDouble");
        Optional.of(menu.stream().collect(Collectors.summingDouble(Dish::getCalories))).ifPresent(System.out::println);
    }

    private static void testSummingLong() {
        System.out.println("testSummingLong");
        Optional.of(menu.stream().collect(Collectors.summingLong(Dish::getCalories))).ifPresent(System.out::println);
    }

    private static void testSummingInt() {
        System.out.println("testSummingInt");
        Optional.of(menu.stream().collect(Collectors.summingInt(Dish::getCalories))).ifPresent(System.out::println);
    }

    private static void testToCollection() {
        System.out.println("testToCollection");
        Optional.of(menu.stream().filter(s -> s.getCalories() > 600).collect(Collectors.toCollection(LinkedList::new))).ifPresent(System.out::println);
    }

    private static void testToConcurrentMap() {
        System.out.println("testToConcurrentMap");
        Optional.of(menu.stream().collect(Collectors.toConcurrentMap(Dish::getName, Dish::getCalories))).ifPresent(s -> {
            System.out.println(s);
            System.out.println(s.getClass());
        });
    }

    private static void testToMap() {
        System.out.println("testToMap");

        // 线程安全 活学活用
        Optional.of(menu.stream().collect(Collectors.collectingAndThen(Collectors.toMap(Dish::getName, Dish::getCalories), Collections::synchronizedMap))).ifPresent(s -> {
            System.out.println(s);
            System.out.println(s.getClass());
        });

        Optional.of(menu.stream().collect(Collectors.toMap(Dish::getName, Dish::getCalories))).ifPresent(s -> {
            System.out.println(s);
            System.out.println(s.getClass());
        });
    }

    /**
     * Type:Total
     */
    private static void testToConcurrentMapWithBinaryOperator() {
        System.out.println("testToConcurrentMapWithBinaryOperator");
        Optional.of(menu.stream().collect(Collectors.toConcurrentMap(Dish::getType, v -> 1L, (a, b) -> a + b))).ifPresent(s -> {
            System.out.println(s);
            System.out.println(s.getClass());
        });
    }

    /**
     * Type:Total
     */
    private static void testToConcurrentMapWithBinaryOperatorAndSupplier() {
        System.out.println("testToConcurrentMapWithBinaryOperatorAndSupplier");
        Optional.of(menu.stream().collect(Collectors.toConcurrentMap(Dish::getType, v -> 1L, (a, b) -> a + b, ConcurrentSkipListMap::new))).ifPresent(s -> {
            System.out.println(s);
            System.out.println(s.getClass());
        });
    }

    private static void testToList() {
        System.out.println("testToList");
        Optional.of(menu.stream().collect(Collectors.toList())).ifPresent(v -> {
            System.out.println(v);
            System.out.println(v.getClass());
        });
    }

    private static void testToSet() {
        System.out.println("testToSet");
        Optional.of(menu.stream().collect(Collectors.toSet())).ifPresent(v -> {
            System.out.println(v);
            System.out.println(v.getClass());
        });
    }
}
