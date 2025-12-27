package Pizzeria.repository;

import Pizzeria.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    Pizza findBySlug(String slug);

    @Query("SELECT DISTINCT p FROM Pizza p LEFT JOIN FETCH p.sizes")
    List<Pizza> findAllWithSizes();

    @Query("""
    SELECT DISTINCT p
    FROM Pizza p
    LEFT JOIN FETCH p.sizes
    LEFT JOIN p.tags t
    WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :q, '%'))
""")
    List<Pizza> searchByName(@Param("q") String q);


    @Query("""
    SELECT DISTINCT p
    FROM Pizza p
    LEFT JOIN FETCH p.sizes
    LEFT JOIN p.tags t
    WHERE t.id = :tagId
""")
    List<Pizza> findByTagId(@Param("tagId") Integer tagId);


    @Query("""
    SELECT DISTINCT p
    FROM Pizza p
    LEFT JOIN FETCH p.sizes
    LEFT JOIN p.tags t
    WHERE t.id = :tagId
      AND LOWER(p.name) LIKE LOWER(CONCAT('%', :q, '%'))
""")
    List<Pizza> searchByNameAndTag(
            @Param("q") String q,
            @Param("tagId") Integer tagId
    );

}
