package com.video.videostreaming.service.impl;

import java.util.List;

import com.video.videostreaming.model.entity.GenreMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.video.videostreaming.model.repository.GenreMovieRepo;
import com.video.videostreaming.service.GenreMovieService;

@Service
public class GenreMovieServiceImpl implements GenreMovieService  {

    @Autowired
    private GenreMovieRepo genreMovieRepo;

    @Override
    public List<GenreMovie> findAll() {
        return genreMovieRepo.findAll();
    }

    @Override
    public GenreMovie findById(Long id) {
        return genreMovieRepo.findById(id).orElse(new GenreMovie());
    }

    @Override
    public GenreMovie save(GenreMovie genreMovie) {
        return genreMovieRepo.save(genreMovie);
    }

    @Override
    public void delete(Long id) {
        genreMovieRepo.deleteById(id);
    }

    @Override
    public List<GenreMovie> findByGenreId(Long id) {
        return genreMovieRepo.findByGenreId(id);
    }

    @Override
    public void updateGenreNameByGenreId(String genreName, Long id) {
        genreMovieRepo.updateGenreNameByGenreId(genreName, id);
    }
    
}
