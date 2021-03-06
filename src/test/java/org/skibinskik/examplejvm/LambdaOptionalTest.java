package org.skibinskik.examplejvm;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.*;

@Slf4j
public class LambdaOptionalTest {

    private static List<String> names;

    @BeforeAll
    static void initAll() {
        names = Arrays.asList("peter", "anna", "mike", "xenia");
    }

    @Test
    void testLambda() {
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });

        Collections.sort(names, (String a, String b) -> {
            return b.compareTo(a);
        });

        Collections.sort(names, (String a, String b) -> b.compareTo(a));

        names.sort((a, b) -> b.compareTo(a));

        names.sort(Comparator.reverseOrder());
    }

    @Test
    void testOptional() {
        Optional<String> optional = Optional.of("bam");

        optional.isPresent();           // true
        optional.get();                 // "bam"
        optional.orElse("fallback");    // "bam"

        optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"
    }

}
