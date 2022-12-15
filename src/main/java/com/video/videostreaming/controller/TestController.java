package com.video.videostreaming.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.video.videostreaming.service.VideoServices;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @Autowired
    private VideoServices videoServices;
    
    @GetMapping("/hello")
    public String getHello(){
        return "hellow World";
    }

    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllVideoNames(){
        return ResponseEntity.ok(videoServices.getAllVideoNames());
    }
}
