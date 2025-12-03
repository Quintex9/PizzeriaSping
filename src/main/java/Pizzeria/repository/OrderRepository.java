package Pizzeria.repository;

import Pizzeria.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByCustomerId(Integer customerId);

    List<Order> findByStatus(String status);

    List<Order> findByAssignedCookId(Integer cookId);

    List<Order> findByAssignedCourierId(Integer courierId);
}
