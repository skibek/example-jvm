package org.skibinskik.examplejvm.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * https://howtodoinjava.com/puzzles/fizzbuzz-solution-java/
 *
 * @author x,y,z
 * @since 2020.03.14
 * @version 1.0
 * @hidden
 */
@Slf4j
public class ExampleFizzBuzzAlgorithmTest {

    @BeforeAll
    static void initAll() {
    }

    @Test
    void testResolution() {
        IntStream.rangeClosed(1, 100)
                .mapToObj(i -> i % 5 == 0 ? (i % 7 == 0 ? i + " FizzBuzz" :  i + " Fizz") : (i % 7 == 0 ? i + " Buzz" : i))
                .forEach(System.out::println);
    }

    @Test
    void testBeforeJava8() {
        for (int i = 1; i <= 100; i++)
        {
            if (((i % 5) == 0) && ((i % 7) == 0)) // Is it a multiple of 5 & 7?
                System.out.println(i + " fizzbuzz");
            else if ((i % 5) == 0) // Is it a multiple of 5?
                System.out.println(i + " fizz");
            else if ((i % 7) == 0) // Is it a multiple of 7?
                System.out.println(i + " buzz");
            else
                System.out.println(i); // Not a multiple of 5 or 7
        }
    }

}
