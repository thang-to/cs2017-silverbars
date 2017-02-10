package com.fansipan.test.cs2017.silverbar.dao;

import com.fansipan.test.cs2017.silverbar.model.Order;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Repository
public class InMemoryOrderDao implements OrderDao {
    private final static int DEFAULT_INITIAL_SIZE = 1000;

    private final Set<Order> orders;


    public InMemoryOrderDao() {
        this(DEFAULT_INITIAL_SIZE);
    }


    public InMemoryOrderDao(int initialSize) {
        orders = Collections.newSetFromMap(new ConcurrentHashMap<>(initialSize));
    }


    @Override
    public Set<Order> findAll() {
        return Collections.unmodifiableSet(orders);
    }


    @Override
    public Set<Order> findByType(Order.Type type) {
        return orders.stream()
                .filter(order -> type == order.type())
                .collect(Collectors.toSet());
    }


    @Override
    public void addOrder(Order order) {
        Validate.notNull(order);

        orders.add(order);
    }


    @Override
    public void removeOrder(Order order) {
        Validate.notNull(order);

        orders.remove(order);
    }
}
