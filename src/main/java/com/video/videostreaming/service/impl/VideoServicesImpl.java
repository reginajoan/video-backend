package com.video.videostreaming.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.video.videostreaming.exception.VideoNotFoundException;
import com.video.videostreaming.model.entity.Video;
import com.video.videostreaming.model.repository.VideoRepo;
import com.video.videostreaming.service.FileStorageService;
import com.video.videostreaming.service.VideoServices;

import reactor.core.publisher.Mono;

@Service
public class VideoServicesImpl implements VideoServices {

    private static final String FORMAT = "classpath:video/%s.mp4";

    @Autowired
    private VideoRepo repo;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public Mono<Resource> getVideo(String name) {
        return Mono.fromSupplier(() -> this.resourceLoader.getResource(String.format(FORMAT, name)));
    }

    @Override
    public void saveVideo(MultipartFile file, Video video) {
        if(repo.existsByName(video.getName())){
            throw new VideoNotFoundException();
        }
        repo.save(video);
        fileStorageService.storeFile(file, video.getName());
    }

    @Override
    public List<String> getAllVideoNames() {
        return repo.getAllEntryNames();
    }

    @Override
    public List<Video> findAll() {
        return repo.findAll();
    }

    @Override
    public boolean deleteVideo(String videoName) {
        Video video = repo.findByName(videoName);
        repo.delete(video);
        return fileStorageService.deleteVideo(videoName);
    }
}