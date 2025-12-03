package Pizzeria.repository;

import Pizzeria.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    Tag findBySlug(String slug);
}
