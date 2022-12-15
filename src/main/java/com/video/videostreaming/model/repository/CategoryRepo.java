package com.video.videostreaming.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.video.videostreaming.model.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    
}
