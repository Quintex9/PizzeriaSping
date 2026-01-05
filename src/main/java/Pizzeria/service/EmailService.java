package Pizzeria.service;

import Pizzeria.entity.Order;

public interface EmailService {

    void sendOrderConfirmation(Order order);
}

