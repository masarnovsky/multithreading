package by.masarnovsky.multithreading.immutablevariables.service;

import by.masarnovsky.multithreading.immutablevariables.model.Item;
import by.masarnovsky.multithreading.immutablevariables.model.Order;
import by.masarnovsky.multithreading.immutablevariables.model.PaymentInfo;
import by.masarnovsky.multithreading.immutablevariables.model.Status;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class OrderService {

    private final ConcurrentHashMap<Long, Order> currentOrders = new ConcurrentHashMap<>();

    public long createOrder(List<Item> items) {
        Order order = new Order(items);
        Long orderId = order.getId();
        currentOrders.put(orderId, order);
        return orderId;
    }

    public void updatePaymentInfo(long cartId, PaymentInfo paymentInfo) {
        Order order = currentOrders.get(cartId);

        order = order.withPaymentInfo(paymentInfo);

        if (order.checkStatus()) {
            deliver(order);
            order = order.withStatus(Status.DELIVERED);
        }
        currentOrders.replace(order.getId(), order);
    }

    public void setPacked(long cartId) {
        Order order = currentOrders.get(cartId);

        order = order.withPacked(true);
        if (order.checkStatus()) {
            deliver(order);
        }
        currentOrders.replace(order.getId(), order);
    }

    private void deliver(Order order) {
        synchronized (order) {
            // logic
        }
    }
}
