package com.video.videostreaming.model.repository;

import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.video.videostreaming.model.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    
    @Query(value = "SELECT * FROM Genre t WHERE t.id=?1", nativeQuery = true)
    public Optional<Genre> findById(Long id);

}
