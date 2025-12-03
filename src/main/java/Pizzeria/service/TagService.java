package Pizzeria.service;

import Pizzeria.entity.Tag;
import java.util.List;

public interface TagService {

    Tag findById(Integer id);

    Tag findBySlug(String slug);

    List<Tag> findAll();

    Tag save(Tag tag);

    void deleteById(Integer id);
}
