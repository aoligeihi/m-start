package com.yy.star.demo.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class StreamMap {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 7, 7, 8);

        List<Integer> result = list.stream().map(i -> i * 2).collect(toList());

        System.out.println(result);

        listDish().stream().map(d -> d.getName()).forEach(System.out::println);

        String[] words = {"Hello", "World"};

        // {h,e,l,l,o},{w,o,r,l,d}
        Stream<String[]> stream = Arrays.stream(words).map(w -> w.split(""));// Stream<String[]>

        // H,e,l,l,o,W,o,r,l,d
        Stream<String> stringStream = stream.flatMap(Arrays::stream);

        stringStream.distinct().forEach(System.out::println);
    }

    private static List<Dish> listDish() {
        return Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));
    }
}
