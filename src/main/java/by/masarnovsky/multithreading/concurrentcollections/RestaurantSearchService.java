package by.masarnovsky.multithreading.concurrentcollections;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class RestaurantSearchService {

    private final ConcurrentHashMap<Restaurant, Integer> stat = new ConcurrentHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();

    public Restaurant getByName(String restaurantName) {
        addToStat(restaurantName);
        return getRestaurantByName(restaurantName);
    }

    public void addToStat(String restaurantName) {
        Restaurant restaurant = getRestaurantByName(restaurantName);
        if (restaurant != null) {
            stat.computeIfPresent(restaurant, (r, i) -> i + 1);
        } else {
            if (lock.tryLock()) {
                lock.lock();
                try {
                    stat.putIfAbsent(new Restaurant(restaurantName), 1);
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public Set<String> printStat() {
        return stat
                .entrySet()
                .stream()
                .map(entry -> entry.getKey().getName() + " - " + entry.getValue())
                .collect(Collectors.toSet());
    }

    private Restaurant getRestaurantByName(String name) {
        return stat
                .keySet()
                .stream()
                .filter(r -> r.getName().equals(name))
                .findFirst().orElse(null);
    }
}
