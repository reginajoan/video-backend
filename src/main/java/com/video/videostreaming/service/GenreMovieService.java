package com.video.videostreaming.service;

import java.util.List;

import com.video.videostreaming.model.entity.GenreMovie;
import org.springframework.stereotype.Service;

@Service
public interface GenreMovieService {

    public List<GenreMovie> findAll();

    public GenreMovie findById(Long id);

    public GenreMovie save(GenreMovie genreBook);

    public void delete(Long id);

    public List<GenreMovie> findByGenreId(Long id);

    public void updateGenreNameByGenreId(String genreName, Long id);
    
}
