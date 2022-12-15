package com.video.videostreaming.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.video.videostreaming.model.entity.Video;

import reactor.core.publisher.Mono;

@Service
public interface VideoServices {
    Mono<Resource> getVideo(String name);

    void saveVideo(MultipartFile file, Video video);

    List<String> getAllVideoNames();

    List<Video> findAll();

    boolean deleteVideo(String videoName);
}
