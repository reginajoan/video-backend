package com.video.videostreaming.service;

import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.video.videostreaming.model.entity.Video;

import reactor.core.publisher.Mono;

@Service
public interface VideoServices {
    Mono<Resource> getVideo(String name);

    Object saveVideo(MultipartFile file, Video video);

    Object save(MultipartFile file, String data);

    List<String> getAllVideoNames();

    List<Video> findAll();

    boolean deleteVideo(String videoName);

    Video findBySecureId(String uuid);

    Optional<Video> findById(Long id);

    Video saveWithGenreBook(MultipartFile file, String data);

}
