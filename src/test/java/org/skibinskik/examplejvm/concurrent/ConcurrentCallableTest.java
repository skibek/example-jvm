package org.skibinskik.examplejvm.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class ConcurrentCallableTest {

    @BeforeAll
    static void initAll() {
    }

    @Test
    void testThread() throws ExecutionException, InterruptedException, TimeoutException {
        Callable<Integer> task = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                return 123;
            }
            catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(task);

        log.info("future done? " + future.isDone());

        Integer result = future.get(1, TimeUnit.SECONDS); //added timeout - throws TimeoutException

        log.info("future done? " + future.isDone());
        log.info("result: " + result);
    }

    @Test
    void testCallableList_all() throws InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                () -> "task1",
                () -> "task2",
                () -> "task3");

        executor.invokeAll(callables)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    }
                    catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(System.out::println);
    }

    Callable<String> callable(String result, long sleepSeconds) {
        return () -> {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return result;
        };
    }

    @Test
    void testCallableList_any() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();
        List<Callable<String>> callables = Arrays.asList(
                callable("task1", 2),
                callable("task2", 1),
                callable("task3", 3));

        String result = executor.invokeAny(callables);
        log.info(result);
    }

    @Test
    void testScheduled() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> log.info("Scheduling: " + System.nanoTime());
        ScheduledFuture<?> future = executor.schedule(task, 3, TimeUnit.SECONDS);

        TimeUnit.MILLISECONDS.sleep(1337);

        long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS); //scheduleAtFixedRate, scheduleWithFixedDelay
        log.info("Remaining Delay:" + remainingDelay + "ms");
    }
}
