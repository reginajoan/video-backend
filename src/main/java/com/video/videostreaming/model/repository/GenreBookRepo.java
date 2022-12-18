package com.video.videostreaming.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.video.videostreaming.model.entity.GenreBook;

public interface GenreBookRepo extends JpaRepository<GenreBook, Long>{
    
    public List<GenreBook> findByGenreId(Long genreId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE genre_book b set genre_name =?1 where b.genre_id=?2", nativeQuery = true)
    public void updateGenreNameByGenreId(String genreName, Long id);

}
