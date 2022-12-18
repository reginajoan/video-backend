package com.video.videostreaming.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.video.videostreaming.model.entity.Genre;

@Service
public interface GenreService {

    public List<Genre> findAll();
    
    public Genre save(Genre genre);

    public Optional<Genre> findById(Long id);

    public void delete(Long id);

    public boolean updateWGenreBooks(Long id, Genre genre);

}
