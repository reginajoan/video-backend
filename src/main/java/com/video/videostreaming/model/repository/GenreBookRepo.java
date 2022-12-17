package com.video.videostreaming.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.video.videostreaming.model.entity.GenreBook;

public interface GenreBookRepo extends JpaRepository<GenreBook, Long>{
    
}
