package com.video.videostreaming.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.video.videostreaming.model.entity.Video;

public interface VideoRepo extends JpaRepository<Video, Long>{

    public Video findByName(String name);

    public boolean existsByName(String name);

    @Query(nativeQuery = true, value = "SELECT name FROM video")
    public List<String> getAllEntryNames();

    public List<Video> findAll();

}
