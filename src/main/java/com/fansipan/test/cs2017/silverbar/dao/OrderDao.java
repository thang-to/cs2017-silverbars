package com.fansipan.test.cs2017.silverbar.dao;

import com.fansipan.test.cs2017.silverbar.model.Order;

import java.util.Set;


public interface OrderDao {

    Set<Order> findAll();

    Set<Order> findByType(Order.Type type);

    void addOrder(Order order);

    void removeOrder(Order order);
}
