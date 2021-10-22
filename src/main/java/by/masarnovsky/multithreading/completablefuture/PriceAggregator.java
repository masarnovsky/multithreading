package by.masarnovsky.multithreading.completablefuture;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class PriceAggregator {

    private final PriceRetriever priceRetriever = new PriceRetriever();

    private final Set<Long> shopIds = Set.of(10L, 45L, 66L, 345L, 234L, 333L, 67L, 123L, 768L);

    public double getMinPrice(long itemId) {
        CompletableFuture<Double>[] tasks = shopIds
                .stream()
                .map(id -> CompletableFuture
                        .supplyAsync(() -> priceRetriever.getPrice(itemId, id))
                        .completeOnTimeout(Double.MAX_VALUE, 2900, TimeUnit.MILLISECONDS))
                .toArray(CompletableFuture[]::new);

        CompletableFuture<Void> all = CompletableFuture.allOf(tasks);

        OptionalDouble min = all
                .thenApply(
                        v -> Arrays.stream(tasks).mapToDouble(CompletableFuture::join).min())
                .join();

        return min.orElse(Double.MAX_VALUE);
    }
}
