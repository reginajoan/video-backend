package com.video.videostreaming.service;

import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.video.videostreaming.model.entity.Video;

import reactor.core.publisher.Mono;

@Service
public interface VideoServices {
    Mono<Resource> getVideo(String name);

    Object saveVideo(MultipartFile file, Video video);

    Object save(MultipartFile file, String data);

    Page<String> getAllVideoNames(Pageable pageable);

    Page<Video> findAll(Pageable pageable);

    boolean deleteVideo(String videoName);

    Video findBySecureId(String uuid);

    Optional<Video> findById(Long id);

    Video saveWithGenreBook(MultipartFile file, String data);
    Page<Video> findSpecification(String name, String description, Pageable pageable);

    Video updateBook(MultipartFile file, String data);

    List<Video> findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCaseOrderByName(String name, String description);

    List<Video> findTop20ByNameContainingIgnoreCaseOrderByNameAsc(String name);
}
