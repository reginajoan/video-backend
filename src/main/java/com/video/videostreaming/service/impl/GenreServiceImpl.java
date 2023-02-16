package com.video.videostreaming.service.impl;

import java.util.List;
import java.util.Optional;

import com.video.videostreaming.model.repository.GenreBookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.video.videostreaming.model.entity.Genre;
import com.video.videostreaming.model.repository.GenreRepository;
import com.video.videostreaming.service.GenreBookService;
import com.video.videostreaming.service.GenreService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreBookRepo genreBookRepo;
    @Autowired
    private GenreRepository repository;
    @Autowired
    private GenreBookService gBookService;
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


    @Override
    public boolean updateWGenreBooks(Long id, Genre genre) {
        //public List<GenreBook> findByGenreId(Long id);
        //List<GenreBook> genreBooks = gBookService.findByGenreId(id);
        log.info("# genre : {}", genre);
        gBookService.updateGenreNameByGenreId(genre.getGenreName(), id);
        //log.info("# gb : {}",gBookService.updateGenreNameByGenreId(genre.getGenreName(), id));
        Genre genre2 = new Genre();
        
        genre2.setGenreName(genre.getGenreName());
        genre2.setId(null);
        Integer update = repository.updateGenreName(genre.getGenreName(), id);
        return update==1 ? true : false;
    }

    @Override
    public List<Long> findIdAll() {
        return repository.findIdAll();
    }

}
