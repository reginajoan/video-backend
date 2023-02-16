package com.video.videostreaming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.video.videostreaming.dto.GenreRequestDTO;
import com.video.videostreaming.dto.ResponseData;
import com.video.videostreaming.model.entity.Genre;
import com.video.videostreaming.service.GenreService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/genre/v1")
public class GenreController {
    
    @Autowired
    private GenreService genreService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        ResponseData response = new ResponseData();
        try {
            response.setStatus(true);
            response.getMessage().add("success to get all data");
            response.setPayload(genreService.findAll());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("error get data : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> createOne(@RequestBody GenreRequestDTO genreRequestDTO){
        ResponseData response = new ResponseData();
        try {
            Genre genre = new Genre();
            genre.setGenreName(genreRequestDTO.getGenreName());
            response.setStatus(true);
            response.getMessage().add("success to save data");
            response.setPayload(genreService.save(genre));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("error get data : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id){
        ResponseData response = new ResponseData();
        try {
            response.setStatus(true);
            response.getMessage().add("find by id : " + id);
            response.setPayload(genreService.findById(id));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("error get data : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateData(@PathVariable("id") Long id, @RequestBody GenreRequestDTO genreRequestDTO){
        ResponseData response = new ResponseData();
        try {
            Genre genre = genreService.findById(id).orElse(new Genre());
            genre.setGenreName(genreRequestDTO.getGenreName());
            response.setStatus(true);
            response.getMessage().add("success update data");
            response.setPayload(genreService.save(genre));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("fail to update data");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/edits/{id}")
    public ResponseEntity<?> updateDataWithGenreBook(@PathVariable("id") Long id, @RequestBody GenreRequestDTO genreRequestDTO){
        ResponseData response = new ResponseData();
        try {
            log.info("#id : {}", id);
            Genre genre = genreService.findById(id).orElse(new Genre());
            genre.setGenreName(genreRequestDTO.getGenreName());
            response.setStatus(true);
            response.getMessage().add("success update data");
            response.setPayload(genreService.updateWGenreBooks(id, genre));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("fail to update data : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
        ResponseData response = new ResponseData();
        try {
            response.setStatus(true);
            response.getMessage().add("find by id : " + id);
            genreService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("error get data : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @GetMapping("/findAllId")
    public ResponseEntity<?> findAllId(){
        ResponseData response = new ResponseData();
        try {
            response.setStatus(true);
            response.getMessage().add("find all id");
            response.setPayload(genreService.findIdAll());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("error get data : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
