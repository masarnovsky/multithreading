package by.masarnovsky.multithreading.completablefuture;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class PriceAggregator {

    private final PriceRetriever priceRetriever = new PriceRetriever();

    private final Set<Long> shopIds = Set.of(10L, 45L, 66L, 345L, 234L, 333L, 67L, 123L, 768L);

    public double getMinPrice(long itemId) {
        List<CompletableFuture<Double>> tasks = shopIds
                .stream()
                .map(id -> CompletableFuture
                        .supplyAsync(() -> priceRetriever.getPrice(itemId, id))
                        .orTimeout(2, TimeUnit.SECONDS)
                        .exceptionally((ex) -> Double.MAX_VALUE))
                .collect(Collectors.toList());

        CompletableFuture<Object> all = CompletableFuture.anyOf(tasks.toArray(new CompletableFuture[tasks.size()]));

        Optional<Double> min = all.thenApply(
                v -> tasks.stream()
                        .map(CompletableFuture::join).min(Comparator.naturalOrder())).join();

        return min.orElse(Double.MAX_VALUE);
    }
}
