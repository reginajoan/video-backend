package com.video.videostreaming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.video.videostreaming.dto.ResponseData;
import com.video.videostreaming.service.GenreBookService;

@RestController
@RequestMapping("/api/genre_book/v1")
public class GenreBookController {
    
    @Autowired
    private GenreBookService bookService;

    @GetMapping("/genre-book/{id}")
    ResponseEntity<?> findByGenreId(@PathVariable("id") Long id){
        ResponseData response = new ResponseData();
        try {
            response.setStatus(true);
            response.getMessage().add("find all by genrebook id : "+ id );
            response.setPayload(bookService.findByGenreId(id));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("error not found data, with error : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/genre-book")
    public ResponseEntity<?> findAll(){
        ResponseData response = new ResponseData();
        try {
            response.setStatus(true);
            response.getMessage().add("find all ");
            response.setPayload(bookService.findAll());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("error not found data, with error : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}