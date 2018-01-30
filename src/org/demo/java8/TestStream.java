package org.demo.java8;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestStream {

    public static void main(String[] args) {

//        testStreamToOther();

//        testMap();

//        testFilter();

        testParallelStream();
    }



    public static void testGenStream() {
        // 1. Individual values
        Stream stream = Stream.of("a", "b", "c");
        stream.forEach(o -> System.out.println(o));

        // 2. Arrays
        String [] strArray = new String[] {"a", "b", "c"};

        stream = Stream.of(strArray);
        stream.forEach(o -> System.out.println(o));

        stream = Arrays.stream(strArray);
        stream.forEach(System.out::println);

        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();
        stream.forEach(o -> System.out.println(o));
    }

    public static void testNumberStream() {
        IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.rangeClosed(1, 3).forEach(System.out::println);
    }

    public static void testStreamToOther() {

        Stream<String> stream = Stream.of("a", "b", "c");

        // 1. Array
        String[] strArray1 = stream.toArray(String[]::new);

        // 2. Collection
        List<String> list1 = stream.collect(Collectors.toList());
        List<String> list2 = stream.collect(Collectors.toCollection(ArrayList::new));

        Set set1 = stream.collect(Collectors.toSet());
        Stack stack1 = stream.collect(Collectors.toCollection(Stack::new));

        // 3. String
        String str = stream.collect(Collectors.joining()).toString();

    }

    public static void testMap() {
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
//        Stream<Integer> outputStream =
                inputStream
                        .flatMap((childList) -> childList.stream())
                        .map(n -> n * n)
                        .forEach(System.out::println);
    }

    public static void testFilter() {
        Integer[] sixNums = {1, 2, 3, 4, 5, 6};
//        Integer[] evens =
//                Stream.of(sixNums).filter(n -> n%2 == 0).toArray(Integer[]::new);
        Stream.of(sixNums).filter(n -> n%2 == 0).forEach(System.out::println);
    }


    public static void testParallelStream() {
        Integer[] sixNums = {1, 2, 3, 4, 5, 6};
        Stream.of(sixNums).parallel().forEach(System.out::println);
    }

    public static void testWhat() {

        String strA = " abcd ", strB = null;
        print(strA);
        print("");
        print(strB);
        getLength(strA);
        getLength("");
        getLength(strB);

    }

    public static void print(String text) {
        // Java 8
        Optional.ofNullable(text).ifPresent(System.out::println);
        // Pre-Java 8
        if (text != null) {
            System.out.println(text);
        }
    }

    public static int getLength(String text) {
        // Java 8
        return Optional.ofNullable(text).map(String::length).orElse(-1);
        // Pre-Java 8
        // return if (text != null) ? text.length() : -1;

    }
}
