package com.fansipan.test.cs2017.silverbar.dao;

import com.fansipan.test.cs2017.silverbar.model.ImmutableOrder;
import com.fansipan.test.cs2017.silverbar.model.Order;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class InMemoryOrderDaoTest {
    private InMemoryOrderDao inMemoryOrderDao;

    @Before
    public void setUp() throws Exception {
        inMemoryOrderDao = new InMemoryOrderDao();
    }

    @Test
    public void testAll() throws Exception {
        Order order1 = ImmutableOrder.builder()
                .userId("u1")
                .quantity(10.2)
                .price(50.4)
                .type(Order.Type.SELL)
                .build();
        inMemoryOrderDao.addOrder(order1);
        assertEquals(ImmutableSet.of(order1), inMemoryOrderDao.findAll());
        assertTrue(inMemoryOrderDao.findByType(Order.Type.BUY).isEmpty());
        assertEquals(ImmutableSet.of(order1), inMemoryOrderDao.findByType(Order.Type.SELL));

        Order order2 = ImmutableOrder.builder()
                .userId("u2")
                .quantity(23.5)
                .price(40.4)
                .type(Order.Type.BUY)
                .build();
        inMemoryOrderDao.addOrder(order2);
        assertEquals(ImmutableSet.of(order1, order2), inMemoryOrderDao.findAll());
        assertEquals(ImmutableSet.of(order2), inMemoryOrderDao.findByType(Order.Type.BUY));
        assertEquals(ImmutableSet.of(order1), inMemoryOrderDao.findByType(Order.Type.SELL));

        Order order3 = ImmutableOrder.builder()
                .userId("u3")
                .quantity(23.5)
                .price(50.4)
                .type(Order.Type.BUY)
                .build();
        inMemoryOrderDao.addOrder(order3);
        assertEquals(ImmutableSet.of(order1, order2, order3), inMemoryOrderDao.findAll());
        assertEquals(ImmutableSet.of(order2, order3), inMemoryOrderDao.findByType(Order.Type.BUY));
        assertEquals(ImmutableSet.of(order1), inMemoryOrderDao.findByType(Order.Type.SELL));

        inMemoryOrderDao.removeOrder(order1);
        assertEquals(ImmutableSet.of(order2, order3), inMemoryOrderDao.findAll());
        assertEquals(ImmutableSet.of(order2, order3), inMemoryOrderDao.findByType(Order.Type.BUY));
        assertTrue(inMemoryOrderDao.findByType(Order.Type.SELL).isEmpty());
    }

}