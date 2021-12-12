package by.masarnovsky.multithreading.immutablevariables.model;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public final class Order {
    private final static AtomicLong nextId = new AtomicLong(0L);

    private final Long id;
    private final List<Item> items;
    private PaymentInfo paymentInfo;
    private boolean isPacked;
    private Status status;

    public Order(List<Item> items) {
        this.id = nextId.incrementAndGet();
        this.items = items;
    }

    private Order(Long id, List<Item> items, PaymentInfo paymentInfo, boolean isPacked, Status status) {
        this.id = id;
        this.items = items;
        this.paymentInfo = paymentInfo;
        this.isPacked = isPacked;
        this.status = status;
    }

    public synchronized boolean checkStatus() {
        if (items != null && !items.isEmpty() && paymentInfo != null && isPacked) {
            status = Status.DELIVERED;
            return true;
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public Order withPaymentInfo(PaymentInfo paymentInfo) {
        return new Order(this.id, this.items, paymentInfo, this.isPacked, this.status);
    }

    public boolean isPacked() {
        return isPacked;
    }

    public Order withPacked(boolean packed) {
        return new Order(this.id, this.items, this.paymentInfo, packed, this.status);
    }

    public Status getStatus() {
        return status;
    }

    public Order withStatus(Status status) {
        return new Order(this.id, this.items, this.paymentInfo, this.isPacked, status);
    }
}
