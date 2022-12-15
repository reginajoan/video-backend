package com.video.videostreaming.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.video.videostreaming.model.entity.Category;

@Service
public interface CategoryService {
    
    public List<Category> findAll();
    
    public Category findById(Long id);

    public Category save(Category category);

    public void delete(Long id);
}
