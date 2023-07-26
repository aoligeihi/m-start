package com.yy.star.demo.java8;

import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class SimpleStream {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));
        List<String> dishNameByCollections = getDishNameByCollection(menu);
        System.out.println(dishNameByCollections);
        getDishNamesByStream(menu);
    }

    private static List<String> getDishNamesByStream(List<Dish> menu) {
        return menu.stream().filter(d -> d.getCalories() < 400)
                .sorted(comparing(Dish::getCalories)).map(Dish::getName).collect(toList());
    }

    private static List<String> getDishNameByCollection(List<Dish> menu) {
        ArrayList<Dish> lowCalories = new ArrayList<>();

        // filter and get calories less 400
        for (Dish d : menu) {
            if (d.getCalories() < 400) {
                lowCalories.add(d);
            }

            // sort
            Collections.sort(lowCalories, (d1, d2) -> Integer.compare(d1.getCalories(), d2.getCalories()));
        }
        ArrayList<String> dishNameList = new ArrayList<>();
        for (Dish d : lowCalories) {
            dishNameList.add(d.getName());
        }
        return dishNameList;
    }

    ;

}
