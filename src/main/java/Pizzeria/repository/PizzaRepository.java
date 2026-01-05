package Pizzeria.repository;

import Pizzeria.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    List<Pizza> findByActiveTrue();

    Pizza findBySlug(String slug);

    @Query("SELECT DISTINCT p FROM Pizza p LEFT JOIN FETCH p.sizes WHERE p.id = :id")
    Optional<Pizza> findByIdWithSizes(@Param("id") Integer id);

    @Query("""
        SELECT DISTINCT p
        FROM Pizza p
        WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :q, '%'))
    """)
    List<Pizza> searchByName(@Param("q") String q);

    @Query("""
        SELECT DISTINCT p
        FROM Pizza p
        JOIN p.tags t
        WHERE t.id = :tagId
    """)
    List<Pizza> findByTagId(@Param("tagId") Integer tagId);

    @Query("""
        SELECT DISTINCT p
        FROM Pizza p
        JOIN p.tags t
        WHERE t.id = :tagId
          AND LOWER(p.name) LIKE LOWER(CONCAT('%', :q, '%'))
    """)
    List<Pizza> searchByNameAndTag(
            @Param("q") String q,
            @Param("tagId") Integer tagId
    );
}
