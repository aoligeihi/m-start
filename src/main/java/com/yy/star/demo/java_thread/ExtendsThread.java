package com.yy.star.demo.java_thread;

/**
 * 第 2 种方式是继承 Thread 类，如代码所示，与第 1 种方式不同的是它没有实现接口，而是继承 Thread 类，并重写了其中的 run() 方法。
 * 相信上面这两种方式你一定非常熟悉，并且经常在工作中使用它们。
 */
public class ExtendsThread extends Thread {
    @Override
    public void run() {

        System.out.println("用Thread类实现线程");

    }
}
