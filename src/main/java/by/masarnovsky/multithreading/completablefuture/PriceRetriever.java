package by.masarnovsky.multithreading.completablefuture;

import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class PriceRetriever {

    public double getPrice(long itemId, long shopId) {
        // имитация долгого HTTP-запроса
        int delay = ThreadLocalRandom.current().nextInt(10);
        try { Thread.sleep(delay * 1000);
        } catch (InterruptedException e) {}

        return ThreadLocalRandom.current().nextDouble(1000);
    }

}
