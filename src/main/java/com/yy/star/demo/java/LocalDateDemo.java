package com.yy.star.demo.java;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @program: star
 * @description:
 * @author: yang
 * @create: 2023-07-31 15:48
 **/
public class LocalDateDemo {
    public static void main(String[] args) {
        LocalDate start = LocalDate.now();
//        LocalDate end = LocalDate.parse("2023-07-31");
        LocalDate end = LocalDateTime.now().plusYears(1).withDayOfYear(1).toLocalDate();
        System.out.println(end);
        System.out.println(start.until(end, ChronoUnit.YEARS));// 年
        System.out.println(start.until(end, ChronoUnit.MONTHS));// 月
        System.out.println(start.until(end, ChronoUnit.DAYS));// 日
    }
}
