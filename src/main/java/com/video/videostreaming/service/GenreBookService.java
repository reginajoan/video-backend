package com.video.videostreaming.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.video.videostreaming.model.entity.GenreBook;

@Service
public interface GenreBookService {

    public List<GenreBook> findAll();

    public GenreBook findById(Long id);

    public GenreBook save(GenreBook genreBook);

    public void delete(Long id);

    public List<GenreBook> findByGenreId(Long id);

    public void updateGenreNameByGenreId(String genreName, Long id);
    
}
