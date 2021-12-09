package by.masarnovsky.multithreading.fjp;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class ForkJoinExample {
    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        forkJoinPool.execute(() -> System.out.println(
                IntStream.range(0,10000000).average().getAsDouble()
        ));

        Thread.sleep(1000);
    }
}
