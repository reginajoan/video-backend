package com.video.videostreaming.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.video.videostreaming.dto.ResponseData;
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

    @GetMapping(value = "/video/{title}", produces = "video/mp4")
    public ResponseEntity<?> getVideoByResposeBody(@PathVariable String title, @RequestHeader("Range") String range){
        ResponseData response = new ResponseData();
        try {
            response.setStatus(true);
            response.getMessage().add("successfully get all video names");
            //response.setPayload(videoServices.getVideo(title));
            //.setPayloaMono(videoServices.getVideo(title));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("error get all video name : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
