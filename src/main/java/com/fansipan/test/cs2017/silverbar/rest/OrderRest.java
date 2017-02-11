package com.fansipan.test.cs2017.silverbar.rest;

import com.fansipan.test.cs2017.silverbar.dao.OrderDao;
import com.fansipan.test.cs2017.silverbar.model.Order;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;


@Component
@Api("Orders")
@Path("/orders")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class OrderRest {
    // normally we have a service layer between the rest and the dao layer
    // but we don't have additional business logic for the service layer
    // so use a dao directly from a rest resource
    private final OrderDao orderDao;


    public OrderRest(OrderDao orderDao) {
        this.orderDao = orderDao;
    }


    /**
     * Returns a list of orders by type, aggregated the ones with the same price and sorted by
     * prices ascending for SELL and descending for BUY orders
     *
     * @param type buy or sell
     * @return list of orders
     */
    @ApiOperation("/")
    @Path("/")
    @GET
    public List<Order> findByType(
            @ApiParam(value = "type", required = true) @QueryParam("type") Order.Type type) {
        Set<Order> ordersByType = orderDao.findByType(type);
        if (ordersByType.isEmpty()) {
            return emptyList();
        }

        return ordersByType.stream()
                .collect(groupingBy(order -> Pair.of(order.type(), order.price()))) // group by type and price first
                .values()
                .stream()
                .map(Order::aggregate)                                              // aggregate
                .sorted( (type == Order.Type.BUY)
                        ? comparingDouble(Order::quantity)
                        : comparingDouble(Order::quantity).reversed())              // order by quantity
                .collect(toList());
    }


    @ApiOperation("/")
    @Path("/")
    @POST
    public void addOrder(
            @ApiParam(value = "Order to Add", required = true) Order order) {
        orderDao.addOrder(order);
    }


    @ApiOperation("/")
    @Path("/")
    @DELETE
    public void removeOrder(
            @ApiParam(value = "Order to Remove", required = true) Order order) {
        orderDao.removeOrder(order);
    }
}
