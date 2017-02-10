package com.fansipan.test.cs2017.silverbar.model;

import com.google.common.collect.ImmutableList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class OrderTest {
    private static final double ACCEPTABLE_DELTA = 0.000001;

    @org.junit.Test
    public void aggregate() throws Exception {
        Order order1 = ImmutableOrder.builder()
                .userId("u1")
                .quantity(10.2)
                .price(50.4)
                .type(Order.Type.SELL)
                .build();

        Order aggregateOrder = Order.aggregate(ImmutableList.of(order1));
        assertNotNull(aggregateOrder);
        assertEquals(order1, aggregateOrder);

        Order order2 = ImmutableOrder.builder()
                .userId("u2")
                .quantity(23.5)
                .price(50.4)
                .type(Order.Type.SELL)
                .build();

        aggregateOrder = Order.aggregate(ImmutableList.of(order1, order2));
        assertNotNull(aggregateOrder);
        assertEquals("[u1,u2]", aggregateOrder.userId());
        assertEquals(33.7, aggregateOrder.quantity(), ACCEPTABLE_DELTA);
        assertEquals(50.4, aggregateOrder.price(), ACCEPTABLE_DELTA);
        assertEquals(Order.Type.SELL, aggregateOrder.type());
    }

}