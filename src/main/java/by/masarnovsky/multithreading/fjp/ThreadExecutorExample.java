package by.masarnovsky.multithreading.fjp;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadExecutorExample {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executor.setKeepAliveTime(1, TimeUnit.SECONDS);
        executor.submit(() -> System.out.println(
                IntStream.range(0,10000000).average().getAsDouble()
        ));
    }
}
