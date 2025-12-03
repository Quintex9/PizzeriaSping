package Pizzeria.service;

import Pizzeria.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

    OrderItem findById(Integer id);

    List<OrderItem> findByOrder(Integer orderId);

    OrderItem save(OrderItem item);

    void deleteById(Integer id);
}
