package org.skibinskik.examplejvm.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class StreamTest {

    private static List<String> stringCollection;

    @BeforeAll
    static void initAll() {
        stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");
    }

    @BeforeEach
    void init() {
    }

    @Test
    @DisplayName("╯°□°）╯")
    void testMax() {
        stringCollection
                .stream()
                .sorted()
                //.sorted((a, b) -> b.compareTo(a))
                .filter((s) -> s.startsWith("a"))
                .map(String::toUpperCase)
                //.collect(List)
                .peek(System.out::println)
                .collect(Collectors.toList());
                //.forEach(System.out::println);
        //assertEquals(2, calculator.add(1, 1));
    }

    @Test
    void testMatch() {
        boolean anyStartsWithA =
                stringCollection
                        .stream()
                        .anyMatch((s) -> s.startsWith("a"));


        boolean allStartsWithA =
                stringCollection
                        .stream()
                        .allMatch((s) -> s.startsWith("a"));

        boolean noneStartsWithZ =
                stringCollection
                        .stream()
                        .noneMatch((s) -> s.startsWith("z"));

        long startsWithB =
                stringCollection
                        .stream()
                        .filter((s) -> s.startsWith("b"))
                        .count();
    }

    @Test
    void testReduce() {
        Optional<String> reduced =
                stringCollection
                        .stream()
                        .sorted()
                        .reduce((s1, s2) -> s1 + "#" + s2);
        reduced.ifPresent(System.out::println);
    }

    @Test
    void testCreate() {
        Arrays.asList("a1", "a2", "a3")
                .stream()
                .findFirst()
                .ifPresent(System.out::println);  // a1

        Stream.of("a1", "a2", "a3")
                .findFirst()
                .ifPresent(System.out::println);  // a1

        int i = IntStream.range(1, 4)
                .findFirst()
                .orElse(0);;

        Arrays.stream(new int[] {1, 2, 3})
                .map(n -> 2 * n + 1)
                .average()
                .ifPresent(System.out::println);  // 5.0


        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .anyMatch(s -> {
                    System.out.println("anyMatch: " + s);
                    return s.startsWith("A");
                });


        Supplier<Stream<String>> streamSupplier =
                () -> Stream.of("d2", "a2", "b1", "b3", "c")
                        .filter(s -> s.startsWith("a"));

        boolean anyFlag = streamSupplier.get().anyMatch(s -> true);   // ok
        log.info("anyFlag=" + anyFlag);
        boolean noneFlag = streamSupplier.get().noneMatch(s -> true);  // ok
        log.info("noneFlag=" + noneFlag);
    }

    @Test
    void testDisplayBadReordering() {
        Stream.of("d2", "a2", "b1", "b3", "c")
                .sorted((s1, s2) -> {
                    System.out.printf("sort: %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));
    }

    @Test
    public void whenStreamPartition_thenGetMap() {
        List<Integer> intList = Arrays.asList(2, 4, 5, 6, 8);
        Map<Boolean, List<Integer>> isEven = intList.stream().collect(
                Collectors.partitioningBy(i -> i % 2 == 0));

        assertEquals(isEven.get(true).size(), 4);
        assertEquals(isEven.get(false).size(), 1);
    }

    @Test
    public void whenGenerateStream_thenGetInfiniteStream() {
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);

        Stream<Integer> evenNumStream = Stream.iterate(2, i -> i * 2);

        List<Integer> collect = evenNumStream
                .limit(5)
                .collect(Collectors.toList());

        assertEquals(collect, Arrays.asList(2, 4, 8, 16, 32));
    }

}
