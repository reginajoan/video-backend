package com.video.videostreaming.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.video.videostreaming.exception.CategoryException;
import com.video.videostreaming.model.entity.Category;
import com.video.videostreaming.model.repository.CategoryRepo;
import com.video.videostreaming.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepo.findById(id).orElse(null);
    }

    @Override
    public Category save(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public void delete(Long id) {
        if(categoryRepo.findById(id).isEmpty()){
            throw new CategoryException("category not found");
        }
        else{
            categoryRepo.deleteById(id);
        }
    }
}
