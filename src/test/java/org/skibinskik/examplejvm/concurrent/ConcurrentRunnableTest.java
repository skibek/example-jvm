package org.skibinskik.examplejvm.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ConcurrentRunnableTest {

    @BeforeAll
    static void initAll() {
    }

    @Test
    void testThread() {
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            log.info("Hello " + threadName);
        };
        task.run();

        Thread thread = new Thread(task);
        thread.start();

        log.info("Done!");
    }

    @Test
    void testThread2() {
        Runnable runnable = () -> {
            try {
                String name = Thread.currentThread().getName();
                log.info("Foo " + name);
                TimeUnit.SECONDS.sleep(1);
                log.info("Bar " + name);
            }
            catch (InterruptedException e) {
                log.error("error", e);
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        log.info("Done!");
    }

    @Test
    void testExecutor() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("Hello " + threadName);
        });

        try {
            log.info("attempt to shutdown executor");
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            log.error("tasks interrupted");
        }
        finally {
            if (!executor.isTerminated()) {
                log.error("cancel non-finished tasks");
            }
            executor.shutdownNow();
            log.info("shutdown finished");
        }

    }

}
