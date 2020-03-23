package org.skibinskik.examplejvm.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Get n fibonacci elements -
 * one for - Linear O(n)
 * Recursion - Exponential Time - O(k^n)
 */
@Slf4j
public class ExampleFibonacciAlgorithmTest {

    //Integer - for check rollout
    List<Double> list = new ArrayList<>();
    int n = 100;

    @BeforeEach
    void init() {
        list.add(1d);
    }

    @Test
    void testResolution() {
        //[1, 1, 2, 3, 5, 8, 13, 21 ...]
        int counter = 0;
        for (int i = 0; i < n; i++) {
            double sum = -1;
            if (counter == 0) {
                sum = list.get(0);
                log.info("Step - counter=" + counter + " sum=" + sum);
            } else {
                sum = list.get(counter) + list.get(counter - 1);
                log.info("Step - counter=" + counter + " sum=" + sum + " x=" + list.get(counter) + " x-1=" + list.get(counter - 1));
            }
            /*
            if (sum < 0) {
                //counter are rollout for int
                log.info("Rollout - counter=" + counter);
                break;
            }
            */
            list.add(sum);
            counter++;
        }


        log.info("Znalezione = " + list);
    }

    //without array
    @Test
    void testResolution2() {
        int nextTerm = 0, first = 0, second = 1;
        for (int i = 0; i < n; i++) {
            if (i <= 1) {
                nextTerm = i;
            } else {
                nextTerm = first + second;
                first = second;
                second = nextTerm;
            }
            log.info("nextTerm = " + nextTerm);

            if (nextTerm < 0) {
                //counter are rollout for int
                log.info("Rollout - counter=" + i);
                break;
            }
        }
    }


    int n1=0,n2=1,n3=0;

    @Test
    void testResolutionRecursion() {
        int count=10;
        log.info(n1+" "+n2);//printing 0 and 1
        printFibonacci(count-2);//n-2 because 2 numbers are already printed
    }

    private void printFibonacci(int count){
        if(count>0){
            n3 = n1 + n2;
            n1 = n2;
            n2 = n3;
            System.out.print(" "+n3);
            printFibonacci(count-1);
        }
    }
}
