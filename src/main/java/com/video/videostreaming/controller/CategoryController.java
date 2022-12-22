package com.video.videostreaming.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.video.videostreaming.dto.CategoryRequestDTO;
import com.video.videostreaming.dto.ResponseData;
import com.video.videostreaming.model.entity.Category;
import com.video.videostreaming.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/category/v1")
@PreAuthorize("isAuthenticated()")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        ResponseData response = new ResponseData();
        try {
            log.info("# => this is Category Controller Find All");
            response.setStatus(true);
            response.setPayload(categoryService.findAll());
            response.getMessage().add("All Category");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("Fail to get All Category");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CategoryRequestDTO cDto){
        ResponseData response = new ResponseData();
        try {
            Category category = new Category();
            category.setCategory(cDto.getCategory());
            category.setSecureId(UUID.randomUUID().toString());
            response.setStatus(true);
            response.getMessage().add("Success save category");
            response.setPayload(categoryService.save(category));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("Failed to save new category, error : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findCategoryById(@PathVariable Long id){
        ResponseData response = new ResponseData();
        try {
            response.setStatus(true);
            response.getMessage().add("Success to get data by id : " + id);
            response.setPayload(categoryService.findById(id));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("Fail to get data by id : " + id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        ResponseData response = new ResponseData();
        try {
            response.setStatus(true);
            response.getMessage().add("Success to delete category id : " + id);
            categoryService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("Fail to delete category id : " + id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
