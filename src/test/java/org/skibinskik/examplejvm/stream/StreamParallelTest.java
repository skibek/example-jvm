package org.skibinskik.examplejvm.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.skibinskik.examplejvm.stream.model.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@Slf4j
public class StreamParallelTest {

    private static List<String> values;
    private static List<Person> persons;

    @BeforeAll
    static void initAll() {
        int max = 1000000;
        values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        persons =
                Arrays.asList(
                        new Person("Max", 18),
                        new Person("Peter", 23),
                        new Person("Pamela", 23),
                        new Person("David", 12));
    }

    @Test
    void testParallelEasy() {
        long t0 = System.nanoTime();
        long count = values.stream().sorted().count();
        log.info(String.valueOf(count));
        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        long micros = TimeUnit.NANOSECONDS.toMicros(t1 - t0);
        //log.info(t1 + " " + t0);
        System.out.println(String.format("sequential sort took: %d ms %d micros", millis, micros));


        t0 = System.nanoTime();
        count = values.parallelStream().sorted().count();
        log.info(String.valueOf(count));
        t1 = System.nanoTime();
        millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        micros = TimeUnit.NANOSECONDS.toMicros(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms %d micros", millis, micros));
    }

    @Test
    void testReduce() {
        Integer ageSum = persons
                .stream()
                .reduce(0,
                        (sum, p) -> {
                            System.out.format("accumulator: sum=%s; person=%s\n", sum, p);
                            return sum += p.age;
                        },
                        (sum1, sum2) -> {
                            System.out.format("combiner: sum1=%s; sum2=%s\n", sum1, sum2);
                            return sum1 + sum2;
                        });

        // accumulator: sum=0; person=Max
        // accumulator: sum=18; person=Peter
        // accumulator: sum=41; person=Pamela
        // accumulator: sum=64; person=David


        //parallel
        ageSum = persons
                .parallelStream()
                .reduce(0,
                        (sum, p) -> {
                            System.out.format("accumulator: sum=%s; person=%s\n", sum, p);
                            return sum += p.age;
                        },
                        (sum1, sum2) -> {
                            System.out.format("combiner: sum1=%s; sum2=%s\n", sum1, sum2);
                            return sum1 + sum2;
                        });

        // accumulator: sum=0; person=Pamela
        // accumulator: sum=0; person=David
        // accumulator: sum=0; person=Max
        // accumulator: sum=0; person=Peter
        // combiner: sum1=18; sum2=23
        // combiner: sum1=23; sum2=12
        // combiner: sum1=41; sum2=35
    }

    @Test
    void testRealPerallel() {
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        log.info("ForkJoinPool=" + commonPool.getParallelism());    // 3
        //-Djava.util.concurrent.ForkJoinPool.common.parallelism=5

        log.info("a1...");
        Arrays.asList("a1", "a2", "b1", "c2", "c1")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n", s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n", s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .sorted((s1, s2) -> {
                    System.out.format("sort: %s <> %s [%s]\n", s1, s2, Thread.currentThread().getName());
                    return s1.compareTo(s2);
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n", s, Thread.currentThread().getName()));

        log.info("persons...");
        persons
                .parallelStream()
                .reduce(0,
                        (sum, p) -> {
                            System.out.format("accumulator: sum=%s; person=%s [%s]\n", sum, p, Thread.currentThread().getName());
                            return sum += p.age;
                        },
                        (sum1, sum2) -> {
                            System.out.format("combiner: sum1=%s; sum2=%s [%s]\n", sum1, sum2, Thread.currentThread().getName());
                            return sum1 + sum2;
                        });
    }
}
