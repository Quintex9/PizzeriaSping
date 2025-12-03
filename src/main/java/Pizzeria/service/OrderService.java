package Pizzeria.service;

import Pizzeria.entity.Order;

import java.util.List;

public interface OrderService {

    Order findById(Integer id);

    List<Order> findByCustomer(Integer customerId);

    List<Order> findByStatus(String status);

    Order save(Order order);

    void deleteById(Integer id);
}
