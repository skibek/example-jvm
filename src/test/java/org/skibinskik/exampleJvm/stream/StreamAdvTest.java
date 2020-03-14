package org.skibinskik.exampleJvm.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skibinskik.exampleJvm.stream.model.Person;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * https://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
 */
@Slf4j
public class StreamAdvTest {

    private static List<Person> persons;

    @BeforeAll
    static void initAll() {
        persons =
            Arrays.asList(
                    new Person("Max", 18),
                    new Person("Peter", 23),
                    new Person("Pamela", 23),
                    new Person("David", 12));
    }

    @Test
    void testMax() {
        List<Person> filtered =
                persons
                        .stream()
                        .filter(p -> p.name.startsWith("P"))
                        .collect(Collectors.toList());
        log.info(String.valueOf(filtered));    // [Peter, Pamela]


        Map<Integer, List<Person>> personsByAge = persons
                .stream()
                .collect(Collectors.groupingBy(p -> p.age));
        personsByAge
                .forEach((age, p) -> log.info("age " + age + ": " + p + "\n"));
        // age 18: [Max]
        // age 23: [Peter, Pamela]
        // age 12: [David]


        Double averageAge = persons
                .stream()
                .collect(Collectors.averagingInt(p -> p.age));
        log.info(String.valueOf(averageAge));     // 19.0


        IntSummaryStatistics ageSummary =
                persons
                        .stream()
                        .collect(Collectors.summarizingInt(p -> p.age));
        log.info(String.valueOf(ageSummary));
        // IntSummaryStatistics{count=4, sum=76, min=12, average=19.000000, max=23}


        String phrase = persons
                .stream()
                .filter(p -> p.age >= 18)
                .map(p -> p.name)
                .collect(Collectors.joining(
                        " and ", // delimiter
                        "In Germany ", // prefix
                        " are of legal age.")); // suffix

        log.info(phrase);
        // In Germany Max and Peter and Pamela are of legal age.


        Collector<Person, StringJoiner, String> personNameCollector =
                Collector.of(
                        () -> new StringJoiner(" | "),          // supplier
                        (j, p) -> j.add(p.name.toUpperCase()),  // accumulator
                        (j1, j2) -> j1.merge(j2),               // combiner
                        StringJoiner::toString);                // finisher
        String names = persons
                .stream()
                .collect(personNameCollector);
        log.info(names);  // MAX | PETER | PAMELA | DAVID


        persons
                .stream()
                .reduce((p1, p2) -> p1.age > p2.age ? p1 : p2)
                .ifPresent(System.out::println);    // Pamela

        Person result =
                persons
                        .stream()
                        .reduce(new Person("", 0), (p1, p2) -> {
                            p1.age += p2.age;
                            p1.name += p2.name;
                            return p1;
                        });

        System.out.format("name=%s; age=%s", result.name, result.age);
        // name=MaxPeterPamelaDavid; age=76
    }

}
