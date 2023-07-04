package com.yy.star.demo.java_thread;

/**
 * 第 1 种方式是通过实现 Runnable 接口实现多线程，
 * 首先通过 RunnableThread 类实现 Runnable 接口，然后重写 run() 方法，之后只需要把这个实现了 run() 方法的实例传到 Thread 类中就可以实现多线程。
 */
public class RunnableThread implements Runnable{
    @Override
    public void run() {
        System.out.println("用实现Runnable接口实现线程");
    }
}
