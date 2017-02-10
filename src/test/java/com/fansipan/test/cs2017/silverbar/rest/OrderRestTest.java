package com.fansipan.test.cs2017.silverbar.rest;

import com.fansipan.test.cs2017.silverbar.dao.OrderDao;
import com.fansipan.test.cs2017.silverbar.model.ImmutableOrder;
import com.fansipan.test.cs2017.silverbar.model.Order;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class OrderRestTest {
    @Mock
    private OrderDao orderDao;

    @InjectMocks
    private OrderRest orderRest;

    @Test
    public void findByTypeEmpty() throws Exception {
        Order.Type type = Order.Type.BUY;
        when(orderDao.findByType(type))
                .thenReturn(Collections.emptySet());

        assertEquals(Collections.emptyList(), orderRest.findByType(type));
    }


    @Test
    public void findByType() throws Exception {
        // test data
        Order s1 = createOrder("u1", 3.5, 306, Order.Type.SELL);
        Order s2 = createOrder("u2", 1.2, 310, Order.Type.SELL);
        Order s3 = createOrder("u3", 1.5, 307, Order.Type.SELL);
        Order s4 = createOrder("u4", 2.0, 306, Order.Type.SELL);
        Order b5 = createOrder("u5", 12, 310, Order.Type.BUY);
        Order b6 = createOrder("u6", 2, 305, Order.Type.BUY);
        Order b7 = createOrder("u7", 4, 305, Order.Type.BUY);
        Order b8 = createOrder("u8", 5, 305, Order.Type.BUY);

        when(orderDao.findByType(Order.Type.SELL))
                .thenReturn(ImmutableSet.of(s1, s2, s3, s4));
        when(orderDao.findByType(Order.Type.BUY))
                .thenReturn(ImmutableSet.of(b5, b6, b7, b8));

        // expect [s1, s4], s3, s2
        assertEquals(ImmutableList.of(Order.aggregate(s1, s4), s3, s2), orderRest.findByType(Order.Type.SELL));

        // expect [b6, b7, b8], b5
        assertEquals(ImmutableList.of(Order.aggregate(b6, b7, b8), b5), orderRest.findByType(Order.Type.BUY));

        Mockito.verify(orderDao, times(1)).findByType(Order.Type.SELL);
        Mockito.verify(orderDao, times(1)).findByType(Order.Type.BUY);
    }


    @Test
    public void addOrder() throws Exception {
        Order order1 = createOrder("u1", 23.5, 50.4, Order.Type.SELL);
        orderRest.addOrder(order1);

        Mockito.verify(orderDao, times(1)).addOrder(order1);
    }


    @Test
    public void removeOrder() throws Exception {
        Order order2 = createOrder("u2", 3000, 200, Order.Type.BUY);
        orderRest.removeOrder(order2);

        Mockito.verify(orderDao, times(1)).removeOrder(order2);
    }


    private Order createOrder(String userId, double quantity, double price, Order.Type type) {
        return ImmutableOrder.builder()
                .userId(userId)
                .quantity(quantity)
                .price(price)
                .type(type)
                .build();
    }
}