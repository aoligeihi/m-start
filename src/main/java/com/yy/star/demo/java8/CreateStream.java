package com.yy.star.demo.java8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class CreateStream {
    public static void main(String[] args) {

    }

    /**
     * Generate the stream object from collection
     *
     * @return
     */
    private Stream<String> createStreamFromCollection() {

        List<String> list = Arrays.asList("hello", "alex", "wangwenjun", "world", "stream");
        return list.stream();
    }

    private static Stream<String> createStreamFromValues() {
        return Stream.of("hello", "alex", "wangwenjun", "world", "stream");
    }

    private static Stream<String> createStreamFromArrays() {
        return Arrays.stream(new String[]{"hello", "alex", "wangwenjun", "world", "stream"});
    }

    private static Stream<String> createStreamFromFile() {
        Path path = Paths.get("D:\\ideaworkspace\\star\\src\\main\\java\\com\\yy\\star\\demo\\java\\DelayTest.java");
        try (Stream<String> streamFromFile = Files.lines(path)) {
            streamFromFile.forEach(System.out::println);
            return streamFromFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
