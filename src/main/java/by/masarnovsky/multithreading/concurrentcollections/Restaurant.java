package by.masarnovsky.multithreading.concurrentcollections;

import java.util.Objects;
import java.util.UUID;

public class Restaurant {

    private String name;

    public Restaurant() {
        this.name = UUID.randomUUID().toString();
    }

    public Restaurant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
