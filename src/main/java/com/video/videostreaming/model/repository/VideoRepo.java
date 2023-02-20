package com.video.videostreaming.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.video.videostreaming.model.entity.Video;

public interface VideoRepo extends JpaRepository<Video, Long>{

    public Video findByName(String name);

    public boolean existsByName(String name);

    @Query(nativeQuery = true, value = "SELECT name FROM video")
    public Page<String> getAllEntryNames(Pageable pageable);

    public List<Video> findAll();

    @Query(value = "SELECT * FROM Video t WHERE t.secure_id=?1", nativeQuery = true)
    public Video findBySecureId(String secureId);

}
