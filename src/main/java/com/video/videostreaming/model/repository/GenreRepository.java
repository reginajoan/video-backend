package com.video.videostreaming.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.video.videostreaming.model.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    
    @Query(value = "SELECT * FROM Genre t WHERE t.id=?1", nativeQuery = true)
    public Optional<Genre> findById(Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE genre b set genre_name =?1 where b.id=?2", nativeQuery = true)
    public Integer updateGenreName(String genreName, Long id);

    @Query(value = "SELECT id FROM Genre", nativeQuery = true)
    public List<Long> findIdAll();

}
