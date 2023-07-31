package com.yy.star.demo.java;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @program: star
 * @description:
 * @author: yang
 * @create: 2023-07-31 15:48
 **/
public class LocalDateDemo {
    public static void main(String[] args) {
        LocalDate start = LocalDate.parse("2023-06-30");
        LocalDate end = LocalDate.parse("2023-07-31");

        System.out.println(start.until(end, ChronoUnit.YEARS));// 年
        System.out.println(start.until(end, ChronoUnit.MONTHS));// 月
        System.out.println(start.until(end, ChronoUnit.DAYS));// 日
    }
}
