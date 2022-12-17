package com.video.videostreaming.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.video.videostreaming.model.entity.Genre;
import com.video.videostreaming.model.repository.GenreRepository;
import com.video.videostreaming.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepository repository;

    @Override
    public List<Genre> findAll() {
        return repository.findAll();
    }

    @Override
    public Genre save(Genre genre) {
        return repository.save(genre);
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
}
