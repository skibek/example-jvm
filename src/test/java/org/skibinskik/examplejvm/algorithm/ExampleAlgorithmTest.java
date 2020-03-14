package org.skibinskik.examplejvm.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Search for first element not in right order - from 1 to max
 *
 * @author x,y,z
 * @since 2020.03.14
 * @version 1.0
 * @hidden https://medium.com/@rhamedy/a-short-summary-of-java-coding-best-practices-31283d0167d3
 */
@Slf4j
public class ExampleAlgorithmTest {

    Integer[] array = {8,1,2,2,3,6,4};  //should be 5

    @BeforeAll
    static void initAll() {
    }

    @Test
    void testResolution() {
        Stream<Integer> stream = Stream.of(array);
        List<Integer> list = stream
                .distinct()
                .sorted()
                //.peek(x -> log.info(String.valueOf(x)))
                .collect(Collectors.toList());
        log.info(String.valueOf(list));

        int counter = 1;
        for (Integer integer : list) {
            if (integer == counter) {
                counter++;
            } else {
                break;
            }
        }
        /*
        for (int j = 0; j < list.size(); j++) {
            if (list.get(j) == counter) {
                counter++;
            } else {
                break;
            }
        }
        */

        log.info("Znalezione = " + counter);
    }

}
