package Pizzeria.service;

import Pizzeria.entity.OrderItem;
import Pizzeria.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem findById(Integer id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    @Override
    public List<OrderItem> findByOrder(Integer orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Override
    public OrderItem save(OrderItem item) {
        return orderItemRepository.save(item);
    }

    @Override
    public void deleteById(Integer id) {
        orderItemRepository.deleteById(id);
    }
}
