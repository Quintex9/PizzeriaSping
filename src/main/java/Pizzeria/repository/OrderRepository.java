package Pizzeria.repository;

import Pizzeria.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    //  KUCHÁR – nové nepriradené + pripravované aktuálnym kuchárom
    @Query("""
        SELECT DISTINCT o
        FROM Order o
        LEFT JOIN FETCH o.items
        WHERE (o.status = 'NEW' AND o.assignedCook IS NULL)
           OR (o.status = 'PRIPRAVUJE_SA' AND o.assignedCook.id = :cookId)
        ORDER BY o.status ASC, o.createdAt ASC
    """)
    List<Order> findForCookWithInProgress(@Param("cookId") Integer cookId);

    // KURIÉR – pripravené nepriradené + doručované aktuálnym kuriérom
    @Query("""
        SELECT DISTINCT o
        FROM Order o
        LEFT JOIN FETCH o.items
        WHERE (o.status = 'PRIPRAVENA' AND o.assignedCourier IS NULL)
           OR (o.status = 'DORUCUJE_SA' AND o.assignedCourier.id = :courierId)
        ORDER BY o.status ASC, o.createdAt ASC
    """)
    List<Order> findForCourierWithInProgress(@Param("courierId") Integer courierId);

    // ZÁKAZNÍK – jeho objednávky s položkami
    @Query("""
        SELECT DISTINCT o
        FROM Order o
        LEFT JOIN FETCH o.items
        WHERE o.customer.id = :customerId
    """)
    List<Order> findByCustomerWithItems(@Param("customerId") Integer customerId);

    // 🔔 NOTIFIKÁCIA – počet nových nepriradených objednávok pre kuchára
    @Query("""
        SELECT COUNT(o)
        FROM Order o
        WHERE o.status = 'NEW' AND o.assignedCook IS NULL
    """)
    long countNewUnassignedOrders();

    @Query("""
    SELECT COUNT(o)
    FROM Order o
    WHERE o.customer.id = :userId
""")
    long countByUserId(@Param("userId") Integer userId);
}
