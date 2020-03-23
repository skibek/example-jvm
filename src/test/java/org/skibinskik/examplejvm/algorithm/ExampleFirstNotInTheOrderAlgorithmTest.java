package org.skibinskik.examplejvm.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Search for first element not in right order - from 1 to max
 *
 * @author x,y,z
 * @since 2020.03.14
 * @version 1.0
 * @hidden
 */
@Slf4j
public class ExampleFirstNotInTheOrderAlgorithmTest {

    //Search for first element not in right order - from 1 to max
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
            log.info("Step - integer=" + integer + " counter=" + counter);
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

        log.info("Found = " + counter);
    }

    //https://howtodoinjava.com/puzzles/find-missing-number-from-series/
    @Test
    void testRes2() {
        //10 is missing
        //int[] numbers = {1,2,3,4,5,6,7,8,9, 11,12};
        int[] numbers = {1,2,3,4,6};

        int n = 6; // max from arr
        //int n = 12;
        int idealSum = (n * (n + 1)) / 2;

        //calculateSum
        /*
        int sum = 0;
        for (int n : numbers) {
            sum += n;
        }
        */
        int sum = Arrays.stream(numbers).sum();

        int missingNumber = idealSum - sum;
        log.info(" " + missingNumber);
    }

}
