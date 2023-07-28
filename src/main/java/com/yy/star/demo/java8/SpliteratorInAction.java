package com.yy.star.demo.java8;

import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @program: star
 * @description:
 * @author: yang
 * @create: 2023-07-28 12:00
 **/
public class SpliteratorInAction {

    private static String text = "Lorem ipsum dolor sit,adipiscing"
            + "\n" +
            "Maecenas dictum arcu nec sollictudin,";

    public static void main(String[] args) {
//        IntStream intStream = IntStream.rangeClosed(0, 10);
//        Spliterator.OfInt spliterator = intStream.spliterator();
//        Consumer<Integer> consumer = i -> System.out.println(i);
//        spliterator.forEachRemaining(consumer);
        MySpliteratorText mySpliteratorText = new MySpliteratorText(text);
        Optional.ofNullable(mySpliteratorText.stream().count()).ifPresent(System.out::println);
        mySpliteratorText.stream().forEach(System.out::println);
        mySpliteratorText.stream().filter(s -> !s.equals("")).forEach(System.out::println);
        mySpliteratorText.pralelStream().filter(s -> !s.equals("")).forEach(System.out::println);

    }

    static class MySpliteratorText {
        private final String[] data;

        MySpliteratorText(String text) {
            Objects.requireNonNull(text, "The parameter can not null");
            this.data = text.split("\n");
        }

        public Stream<String> stream() {
            return StreamSupport.stream(new MySpliterator(), false);
        }

        public Stream<String> pralelStream() {
            return StreamSupport.stream(new MySpliterator(), true);
        }

        private class MySpliterator implements Spliterator<String> {

            private int start, end;

            public MySpliterator() {
                this.start = 0;
                this.end = MySpliteratorText.this.data.length - 1;
            }

            public MySpliterator(int start, int end) {
                this.start = start;
                this.end = end;
            }

            @Override
            public boolean tryAdvance(Consumer<? super String> action) {
                if (start <= end) {
                    action.accept(MySpliteratorText.this.data[start++]);
                    return true;
                }
                return false;
            }

            @Override
            public Spliterator<String> trySplit() {
                int mid = (end - start) / 2;
                if (mid <= 1) {
                    return null;
                }

                int left = start;
                int right = start + mid;
                start = start + mid + 1;
                return new MySpliterator(left, right);
            }

            @Override
            public long estimateSize() {
                return end - start;
            }

            @Override
            public long getExactSizeIfKnown() {
                return estimateSize();
            }

            @Override
            public int characteristics() {
                return IMMUTABLE | SIZED | SUBSIZED;
            }
        }

    }
}