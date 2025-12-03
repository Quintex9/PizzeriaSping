package Pizzeria.service;

import Pizzeria.entity.Tag;
import Pizzeria.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag findById(Integer id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public Tag findBySlug(String slug) {
        return tagRepository.findBySlug(slug);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void deleteById(Integer id) {
        tagRepository.deleteById(id);
    }
}
