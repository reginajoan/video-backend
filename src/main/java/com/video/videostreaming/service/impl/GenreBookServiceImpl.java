package com.video.videostreaming.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.video.videostreaming.model.entity.GenreBook;
import com.video.videostreaming.model.repository.GenreBookRepo;
import com.video.videostreaming.service.GenreBookService;

@Service
public class GenreBookServiceImpl implements GenreBookService  {

    @Autowired
    private GenreBookRepo genreBookRepo;

    @Override
    public List<GenreBook> findAll() {
        return genreBookRepo.findAll();
    }

    @Override
    public GenreBook findById(Long id) {
        return genreBookRepo.findById(id).orElse(new GenreBook());
    }

    @Override
    public GenreBook save(GenreBook genreBook) {
        return genreBookRepo.save(genreBook);
    }

    @Override
    public void delete(Long id) {
        genreBookRepo.deleteById(id);
    }

    @Override
    public List<GenreBook> findByGenreId(Long id) {
        return genreBookRepo.findByGenreId(id);
    }

    @Override
    public void updateGenreNameByGenreId(String genreName, Long id) {
        // TODO Auto-generated method stub
        genreBookRepo.updateGenreNameByGenreId(genreName, id);
    }
    
}
