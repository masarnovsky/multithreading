package by.masarnovsky.multithreading.fjp;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.IntStream;

public class ForkJoinExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = (ForkJoinPool) Executors.newWorkStealingPool(4);
        ForkJoinTask<?> fjt = forkJoinPool.submit(() -> System.out.println(
                IntStream.range(0, 10000000).average().getAsDouble()
        ));

        fjt.get();
    }
}
