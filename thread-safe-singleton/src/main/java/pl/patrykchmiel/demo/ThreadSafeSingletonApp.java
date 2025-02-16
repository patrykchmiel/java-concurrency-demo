package pl.patrykchmiel.demo;

import pl.patrykchmiel.demo.singleton.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;


public class ThreadSafeSingletonApp {

    private static final int THREADS_NUMBER = 5;
    private static final int REPEATS_NUMBER = 100;

    public static void main(String[] args) {
        // ========== NON THREAD SAFE SINGLETON ===========
        long startedAt = System.nanoTime();
        System.out.println("Unsafe singleton started. ");
        IntStream.range(0, REPEATS_NUMBER).forEach(i -> runParallel(
                () -> {
                    Thread.sleep(50);
                    return UnsafeSingleton.getInstance();
                }));
        System.out.println("Average time (in nanos): " + (System.nanoTime() - startedAt) / REPEATS_NUMBER);

        // ========== THREAD SAFE BUT "SLOW" ===========
        startedAt = System.nanoTime();
        System.out.println("Synchronized singleton started. ");
        IntStream.range(0, REPEATS_NUMBER).forEach(i -> runParallel(
                () -> {
                    Thread.sleep(50);
                    return LazySynchronizedSingleton.getInstance();
                }));
        System.out.println("Average time (in nanos): " + (System.nanoTime() - startedAt) / REPEATS_NUMBER);

        // ========== THREAD-SAFE WITH DOUBLE-CHECKED LOCKING ===========
        startedAt = System.nanoTime();
        System.out.println("Double checked locking singleton started. ");
        IntStream.range(0, REPEATS_NUMBER).forEach(i -> runParallel(
                () -> {
                    Thread.sleep(50);
                    return LazyDoubleCheckedLockingSingleton.getInstance();
                }));
        System.out.println("Average time (in nanos): " + (System.nanoTime() - startedAt) / REPEATS_NUMBER);

        // ========== EAGER THREAD-SAFE WITH ENUM (PREFERRED) ===========
        startedAt = System.nanoTime();
        System.out.println("Eager enum singleton started. ");
        IntStream.range(0, REPEATS_NUMBER).forEach(i -> runParallel(
                () -> {
                    Thread.sleep(50);
                    return EagerEnumSingleton.getInstance();
                }));
        System.out.println("Average time (in nanos): " + (System.nanoTime() - startedAt) / REPEATS_NUMBER);

        // ========== EAGER THREAD-SAFE AND REFLECTION-SAFE WITH STATIC INITIALIZATION ===========
        startedAt = System.nanoTime();
        System.out.println("Eager thread/reflection safe started. ");
        IntStream.range(0, REPEATS_NUMBER).forEach(i -> runParallel(
                () -> {
                    Thread.sleep(50);
                    return EagerReflectionThreadAndReflectionSafeSingleton.getInstance();
                }));
        System.out.println("Average time (in nanos): " + (System.nanoTime() - startedAt) / REPEATS_NUMBER);

        // ========== EAGER THREAD-SAFE AND REFLECTION-SAFE WITH ENUM (PREFERRED) ===========
        startedAt = System.nanoTime();
        System.out.println("Eager enum singleton started. ");
        IntStream.range(0, REPEATS_NUMBER).forEach(i -> runParallel(
                () -> {
                    Thread.sleep(50);
                    return EagerEnumSingleton.getInstance();
                }));
        System.out.println("Average time (in nanos): " + (System.nanoTime() - startedAt) / REPEATS_NUMBER);


    }

    private static <T> void runParallel(Callable<T> callable) {
        try (ExecutorService executorService = Executors.newFixedThreadPool(THREADS_NUMBER)) {
            List<Callable<T>> callables = new ArrayList<>();
            for (int i = 0; i < THREADS_NUMBER; i++) {
                callables.add(callable);
            }

            List<Future<T>> futures = new ArrayList<>();
            for (Callable<T> c : callables) {
                futures.add(executorService.submit(c));
            }

            futures.stream().parallel().forEach(f -> {
                try {
                    System.out.printf("Callable result: %s%n", f.get());
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Could not get a value from the future");
                }
            });
        }
    }
}
