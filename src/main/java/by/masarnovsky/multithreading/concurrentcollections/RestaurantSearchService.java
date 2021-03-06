package by.masarnovsky.multithreading.concurrentcollections;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

public class RestaurantSearchService {

    private final ConcurrentHashMap<Restaurant, Integer> stat = new ConcurrentHashMap<>();
    private final CopyOnWriteArraySet<Restaurant> restaurants = new CopyOnWriteArraySet<>();

    public Restaurant getByName(String restaurantName) {
        addToStat(restaurantName);
        return getRestaurantByName(restaurantName);
    }

    public void addToStat(String restaurantName) {
        Restaurant restaurant = getRestaurantByName(restaurantName);
        stat.compute(restaurant, (k, v) -> (v != null) ? v + 1 : 1);
    }

    public Set<String> printStat() {
        return stat
                .entrySet()
                .stream()
                .map(entry -> entry.getKey().getName() + " - " + entry.getValue())
                .collect(Collectors.toSet());
    }

    private Restaurant getRestaurantByName(String name) {
        return restaurants
                .stream()
                .filter(restaurant -> restaurant.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
