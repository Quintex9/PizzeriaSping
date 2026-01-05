package Pizzeria.service;

import Pizzeria.entity.Order;
import Pizzeria.entity.User;

import java.util.List;

public interface OrderService {

    Order findById(Integer id);

    List<Order> findForCustomer(Integer customerId);

    List<Order> findForCookWithInProgress();

    List<Order> findForCourierWithInProgress();

    Order save(Order order);

    Order createOrderFromCart(String note);

    void assignCook(Integer orderId);

    void markReady(Integer orderId);

    void assignCourier(Integer orderId);

    void markDelivered(Integer orderId);

    void cancelOrder(Integer orderId);

    List<Order> findAll();

    long countNewUnassignedOrders();

}
