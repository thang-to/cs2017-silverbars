package com.fansipan.test.cs2017.silverbar.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.Validate;
import org.immutables.value.Value;

import java.util.Arrays;
import java.util.Collection;

import static java.util.stream.Collectors.joining;


@Value.Immutable
@JsonSerialize(as = ImmutableOrder.class)
@JsonDeserialize(as = ImmutableOrder.class)
public abstract class Order {
    public enum Type {
        BUY,
        SELL
    }

    public abstract String userId();
    public abstract double quantity();
    public abstract double price();
    public abstract Type type();


    /**
     * Aggregates orders have the same type and price into a larger order. This method
     * doesn't check if the input orders have the same type and price.
     *
     * @param orders orders with the same type and price
     * @return aggregate order
     */
    public static Order aggregate(Collection<Order> orders) {
        Validate.notEmpty(orders);

        Order order = orders.iterator().next();
        if (orders.size() == 1) {
            return order;
        }

        String aggregateUser = orders.stream()
                .map(Order::userId)
                .collect(joining(",", "[", "]"));
        double aggregateQuantity = orders.stream()
                .mapToDouble(Order::quantity)
                .sum();

        return ImmutableOrder.builder()
                .userId(aggregateUser)
                .quantity(aggregateQuantity)
                .price(order.price())
                .type(order.type())
                .build();
    }


    public static Order aggregate(Order ... orders) {
        Validate.notNull(orders);

        return aggregate(Arrays.asList(orders));
    }
}
