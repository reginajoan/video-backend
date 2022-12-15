package com.video.videostreaming.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.video.videostreaming.dto.VideoRequestDTO;
import com.video.videostreaming.exception.RequestException;
import com.video.videostreaming.model.entity.Video;
import com.video.videostreaming.service.CategoryService;
import com.video.videostreaming.service.VideoServices;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/video/v1")
public class VideoController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private VideoServices videoServices;

    @PostMapping(value = "/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> saveVideo(@RequestPart("file") MultipartFile file, @RequestPart("videoDTO") String videoDTO) throws IOException {
        
        log.info("videoDTO : {}", videoDTO);
        log.info("video : {} ", file);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            VideoRequestDTO videoRequestDTO = new VideoRequestDTO();

            videoRequestDTO = objectMapper.readValue(videoDTO, VideoRequestDTO.class);

            log.info("# videoRequestDTO : {}", videoRequestDTO.getCategory());
            Video video = new Video();
            video.setName(videoRequestDTO.getName());
            video.setCategory(categoryService.findById(Long.parseLong(videoRequestDTO.getCategory())));
            video.setDescription(videoRequestDTO.getDescription());
            videoServices.saveVideo(file, video);
        } catch (Exception e) {
            throw new RequestException("error : " + e.getMessage());
        }
        return ResponseEntity.ok("Video saved successfully");
    }

    @GetMapping(value = "/video/{title}", produces = "video/mp4")
    public Mono<Resource> getVideo(@PathVariable String title, @RequestHeader("Range") String range){
        System.out.println("title : " + title);
        System.out.println("range : " + range);
        return videoServices.getVideo(title);
    }

    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllVideoNames(){
        return ResponseEntity.ok(videoServices.getAllVideoNames());
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(videoServices.findAll());
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> delete(@PathVariable("name") String fileName){
        return ResponseEntity.ok(videoServices.deleteVideo(fileName));
    }
}
