package by.masarnovsky.multithreading.completablefuture;

public class CompletableFuturePractice {


    public static void main(String[] args) {
        PriceAggregator priceAggregator = new PriceAggregator();
        long itemId = 12L;

        long start = System.currentTimeMillis();
        double min = priceAggregator.getMinPrice(itemId);
        long end = System.currentTimeMillis();

        System.out.println(min);
        System.out.println((end - start) < 3000); // should be true
    }
}

