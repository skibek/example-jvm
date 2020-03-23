package org.skibinskik.examplejvm.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * https://howtodoinjava.com/puzzles/3-ways-to-calculate-factorial-in-java/
 *
 * in Polish - Silnia
 *
 * BigInteger to hold more bigger values and use below code to get factorial.
 *
 * @author x,y,z
 * @since 2020.03.14
 * @version 1.0
 * @hidden
 */
@Slf4j
public class ExampleFactorialAlgorithmTest {

    int n = 5;

    @BeforeAll
    static void initAll() {
    }

    @Test
    void testResolution() {
        long r = 1;
        for ( long i = 1; i <= n; i++ )
        {
            r *= i;
        }
        log.info(" " + r);
    }

    @Test
    void testRecursive() {
        long l = factorialRecursive(n);
        log.info(" " + l);
    }

    private long factorialRecursive( long n ) {
        return n == 1 ? 1 : n * factorialRecursive( n-1 );
    }

    @Test
    void testStream() {
        long res = LongStream.rangeClosed( 1, n )
                .reduce(1, ( long a, long b ) ->
                        {
                            System.out.println(a + " " + b);
                            return a * b;
                        }
                        );
        log.info(" " + res);
        /*
        Here, LongStream.rangeClosed(2, n) method creates a Stream of longs with the content [2, 3, ... , n].
        reduce (a, b) -> a * b means that each pair a and b – multiply them and return the result. The result then carries over to a for the next round.
        The value “1” used in the reduced method is used as a starting value for variable a for the very first iteration.
         */
    }

}
