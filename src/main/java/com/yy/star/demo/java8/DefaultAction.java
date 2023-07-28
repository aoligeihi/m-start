package com.yy.star.demo.java8;

/**
 * @program: star
 * @description:
 * @author: yang
 * @create: 2023-07-28 14:35
 **/
public class DefaultAction {

//    private void confuse(Object o) {
//        System.out.println("object");
//    }
//
//    private void confuse(int[] i) {
//        System.out.println("int[]");
//    }

    public static void main(String[] args) {
//        A a = () -> 10;
//        System.out.println(a.size());
//        System.out.println(a.isEmpty());
//        DefaultAction action = new DefaultAction();
//        int[] arr = null;
//        Object o = arr;
//        action.confuse(o);
    }

//    private interface A {
//
//        int size();
//
//        default boolean isEmpty() {
//            return size() == 0;
//        }
//    }

    private interface A {

        default void hello() {
            System.out.println("==A.hello==");
        }
    }

    private interface B extends A {
        @Override
        default void hello() {
            System.out.println("==B.hello==");
        }
    }

    /**
     * class 最大 接下来是上一级 最后如果选择仍是混淆的则必须重写争议方法
     */
    private static class C implements A, B {

        @Override
        public void hello() {
//            System.out.println("==C.hello==");
            B.super.hello();
        }

        // 重写 可以调用A,也可以B,也可以用自己的
    }
}
