package Pizzeria.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Pizzeria.cart.CartItem;
import Pizzeria.cart.CartService;
import Pizzeria.entity.Order;
import Pizzeria.entity.OrderItem;
import Pizzeria.entity.OrderStatus;
import Pizzeria.entity.PizzaSize;
import Pizzeria.entity.User;
import Pizzeria.repository.OrderItemRepository;
import Pizzeria.repository.OrderRepository;
import Pizzeria.repository.PizzaSizeRepository;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PizzaSizeRepository pizzaSizeRepository;
    private final CartService cartService;
    private final UserService userService;
    private final EmailService emailService;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            PizzaSizeRepository pizzaSizeRepository,
            CartService cartService,
            UserService userService, EmailService emailService
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.pizzaSizeRepository = pizzaSizeRepository;
        this.cartService = cartService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public Order findById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }


    @Override
    public List<Order> findForCustomer(Integer customerId) {
        return orderRepository.findByCustomerWithItems(customerId);
    }

    @Override
    public List<Order> findForCookWithInProgress() {
        User currentCook = userService.getCurrentUser();
        if (currentCook == null) {
            return List.of();
        }
        return orderRepository.findForCookWithInProgress(currentCook.getId());
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findForCourierWithInProgress() {
        User currentCourier = userService.getCurrentUser();
        if (currentCourier == null) {
            return List.of();
        }
        return orderRepository.findForCourierWithInProgress(currentCourier.getId());
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public long countNewUnassignedOrders() {
        return orderRepository.countNewUnassignedOrders();
    }

    @Override
    @Transactional
    public Order createOrderFromCart(String note) {

        User user = userService.getCurrentUser();

        Order order = new Order();
        order.setCustomer(user);
        order.setCode("ORD-" + System.currentTimeMillis());
        order.setStatus(OrderStatus.NEW);
        order.setTotalPrice(cartService.getTotal());
        order.setDeliveryAddress(user.getAddress());
        order.setNote(note);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);

        for (CartItem item : cartService.getItems()) {

            PizzaSize pizzaSize = pizzaSizeRepository.findById(item.getSizeId())
                    .orElseThrow();

            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setPizzaSize(pizzaSize);
            oi.setQuantity(item.getQuantity());
            oi.setUnitPrice(item.getPrice());

            // SNAPSHOTY
            oi.setPizzaNameSnapshot(item.getPizzaName());
            oi.setSizeLabelSnapshot(item.getSizeLabel());
            oi.setImageUrlSnapshot(pizzaSize.getPizza().getImageUrl());

            orderItemRepository.save(oi);
            order.getItems().add(oi);

        }

        // EMAIL NOTIFIKÁCIA
        emailService.sendOrderConfirmation(order);


        cartService.clear();

        return order;
    }


    // ========= COOK =========

    @Override
    @Transactional
    public void assignCook(Integer orderId) {
        Order order = findById(orderId);
        User current = userService.getCurrentUser();

        // Povolené roly
        boolean allowed = current.hasRole("KUCHAR") || current.hasRole("ADMIN");
        if (!allowed) {
            throw new AccessDeniedException("Nemáte oprávnenie prijať objednávku");
        }

        // Objednávka už bola prijatá
        if (order.getAssignedCook() != null) {
            throw new IllegalStateException("Objednávka je už priradená");
        }

        //  ADMIN aj KUCHÁR sa zapíšu ako kuchár
        order.setAssignedCook(current);
        order.setStatus(OrderStatus.PRIPRAVUJE_SA);
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);
    }


    @Override
    @Transactional
    public void markReady(Integer orderId) {
        Order order = findById(orderId);
        User current = userService.getCurrentUser();

        boolean isAdmin = current.hasRole("ADMIN");

        boolean isAssignedCook =
                order.getAssignedCook() != null &&
                        order.getAssignedCook().getId().equals(current.getId());

        if (!isAdmin && !isAssignedCook) {
            throw new AccessDeniedException(
                    "Objednávku môže dokončiť len ten, kto ju prijal, alebo admin"
            );
        }

        order.setStatus(OrderStatus.PRIPRAVENA);
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);
    }



    // ========= COURIER =========

    @Override
    @Transactional
    public void assignCourier(Integer orderId) {
        Order order = findById(orderId);
        order.setAssignedCourier(userService.getCurrentUser());
        order.setStatus(OrderStatus.DORUCUJE_SA);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void markDelivered(Integer orderId) {
        Order order = findById(orderId);
        order.setStatus(OrderStatus.DORUCENA);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
    }

    // ========= CUSTOMER =========

    @Override
    @Transactional
    public void cancelOrder(Integer orderId) {
        Order order = findById(orderId);
        User currentUser = userService.getCurrentUser();
        
        // Kontrola, či je objednávka zákazníka a je v stave NEW
        if (!order.getCustomer().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("Nemáte oprávnenie zrušiť túto objednávku");
        }
        
        if (order.getStatus() != OrderStatus.NEW) {
            throw new IllegalArgumentException("Objednávku možno zrušiť len v stave NEW");
        }
        
        order.setStatus(OrderStatus.ZRUSENA);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
    }
}
