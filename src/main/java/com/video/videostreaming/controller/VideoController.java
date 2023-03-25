package com.video.videostreaming.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.video.videostreaming.dto.ResponseData;
import com.video.videostreaming.dto.VideoRequestDTO;
import com.video.videostreaming.model.entity.Video;
import com.video.videostreaming.service.VideoServices;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/video/v1")
public class VideoController {

    @Autowired
    private VideoServices videoServices;

    @PostMapping(value = "/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> saveVideoUpdate(@RequestPart("file") MultipartFile file, @RequestPart("videoDTO") String videoDTO) throws IOException {
        ResponseData response = new ResponseData();
        log.info("file : " + file);
        log.info("video : " + videoDTO);
        try {
            Object object = videoServices.saveWithGenreBook(file, videoDTO);
            if(!object.equals(null)){
                response.setStatus(true);
                response.setPayload(object);
                response.getMessage().add("Video saved successfully");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }else{
                response.setStatus(false);
                response.setPayload(object);
                response.getMessage().add("cannot save video : " + object);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("error saved video : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping(value = "/update-new", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateVideoNew(@RequestPart("file") MultipartFile file, @RequestPart("videoDTO") String videoDTO) throws IOException {
        ResponseData response = new ResponseData();
        log.info("file : " + file);
        log.info("video : " + videoDTO);
        try {
            Object object = videoServices.updateBook(file, videoDTO);
            if(!object.equals(null)){
                response.setStatus(true);
                response.setPayload(object);
                response.getMessage().add("Video saved successfully");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }else{
                response.setStatus(false);
                response.setPayload(object);
                response.getMessage().add("cannot save video : " + object);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("error saved video : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping(value = "/video/{title}", produces = "video/mp4")
    public Mono<Resource> getVideo(@PathVariable String title){
        System.out.println("title : " + title);
        //System.out.println("range : " + range);
        return videoServices.getVideo(title);
    }

    // @GetMapping(value = "/video/{title}", produces = "video/mp4")
    // public ResponseEntity<?> getVideoByResposeBody(@PathVariable String title, @RequestHeader("Range") String range){
    //     ResponseData response = new ResponseData();
    //     try {
    //         response.setStatus(true);
    //         response.getMessage().add("successfully get all video names");
    //         //response.setPayload(videoServices.getVideo(title));
    //         response.setPayloaMono(videoServices.getVideo(title));
    //         return ResponseEntity.status(HttpStatus.OK).body(response);
    //     } catch (Exception e) {
    //         response.setStatus(false);
    //         response.getMessage().add("error get all video name : " + e.getMessage());
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    //     }
    // }

    @GetMapping("/all")
    public ResponseEntity<?> getAllVideoNames(){
        Pageable pageable = PageRequest.of(0, 100);
        ResponseData response = new ResponseData();
        try {
            response.setStatus(true);
            response.getMessage().add("successfully get all video names");
            response.setPayload(videoServices.getAllVideoNames(pageable));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("error get all video name : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        Pageable pageable = PageRequest.of(0,100);
        ResponseData response = new ResponseData();
        try {
            response.setStatus(true);
            response.getMessage().add("successfully get all video");
            response.setPayload(videoServices.findAll(pageable));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("error get all video : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping(value = "/secureId/{secureId}")
    public ResponseEntity<?> findBySecureId(@PathVariable("secureId") String secureId){
        ResponseData response = new ResponseData();
        try {
            Video vid = videoServices.findBySecureId(secureId);
            log.info("vid : {}", vid);
            if(!vid.equals(null)){
                response.setStatus(true);
                response.getMessage().add("get video by secure id : " + secureId);
                response.setPayload(videoServices.findBySecureId(secureId));
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }else{
                response.setStatus(false);
                response.getMessage().add("secure id : "+secureId+" not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("secure id : "+secureId+", error : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping(value = "/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateVideo(@RequestPart("file") MultipartFile file, @RequestPart("videoDTO") String videoDTO) throws IOException {
        ResponseData response = new ResponseData();
        log.info("videoDTO : {}", videoDTO);
        log.info("video : {} ", file);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            VideoRequestDTO videoRequestDTO = new VideoRequestDTO();

            videoRequestDTO = objectMapper.readValue(videoDTO, VideoRequestDTO.class);

            log.info("# videoRequestDTO : {}", videoRequestDTO.getCategory());
            
            Video video = new Video();
            video.setName(videoRequestDTO.getName());
            //video.setCategory(categoryService.findById(Long.parseLong(videoRequestDTO.getCategory())));
            video.setDescription(videoRequestDTO.getDescription());
            Object object = videoServices.saveVideo(file, video);
            log.info("object : {}", object);
            if(object != null){
                response.setStatus(true);
                response.setPayload(object);
                response.getMessage().add("Video saved successfully");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }else{
                response.setStatus(false);
                response.setPayload(object);
                response.getMessage().add("error saved video : ");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            //throw new RequestException("error : " + e.getMessage());
            response.setStatus(false);
            response.getMessage().add("error saved video : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> delete(@PathVariable("name") String fileName){
        ResponseData response = new ResponseData();
        try {
            response.setStatus(true);
            response.getMessage().add("video deleted with name : " + fileName);
            response.setPayload(videoServices.deleteVideo(fileName));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("error delete video "+fileName+" with error : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<?> searching(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String description,
            Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(videoServices.findSpecification(name, description, pageable));
    }
}
