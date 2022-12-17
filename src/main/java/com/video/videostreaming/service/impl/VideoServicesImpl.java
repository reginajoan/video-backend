package com.video.videostreaming.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.video.videostreaming.dto.VideoRequestDTO;
import com.video.videostreaming.exception.VideoNotFoundException;
import com.video.videostreaming.model.entity.Category;
import com.video.videostreaming.model.entity.Genre;
import com.video.videostreaming.model.entity.GenreBook;
import com.video.videostreaming.model.entity.Video;
import com.video.videostreaming.model.repository.VideoRepo;
import com.video.videostreaming.service.CategoryService;
import com.video.videostreaming.service.FileStorageService;
import com.video.videostreaming.service.GenreService;
import com.video.videostreaming.service.VideoServices;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class VideoServicesImpl implements VideoServices {

    private static final String FORMAT = "classpath:video/%s.mp4";

    @Autowired
    private VideoRepo repo;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ResourceLoader resourceLoader;
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GenreService genreService;

    @Override
    public Mono<Resource> getVideo(String name) {
        return Mono.fromSupplier(() -> this.resourceLoader.getResource(String.format(FORMAT, name)));
    }

    @Override
    public Object saveVideo(MultipartFile file, Video video) {
        // try {
        //     if(repo.existsByName(video.getName())){
        //         throw new VideoNotFoundException();
        //     }
        //     video.setVideoSaved(fileStorageService.storeFile(file, video.getName()));
        //     return repo.saveAndFlush(video);
        // } catch (Exception e) {
        //     return e.getMessage();
        // }
        if(repo.existsByName(video.getName())){
            throw new VideoNotFoundException();
        }
        video.setVideoSaved(fileStorageService.storeFile(file, video.getName()));
        // for(Category category : video.getCategory()){
        //     log.info("# video-services-impl video.getCategory : {}", category);
        // }
        return repo.saveAndFlush(video);
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

    @Override
    public Video findBySecureId(String uuid) {
        return repo.findBySecureId(uuid);
    }

    @Override
    public Optional<Video> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Object save(MultipartFile file, String videoDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        VideoRequestDTO videoRequestDTO = new VideoRequestDTO();
        List<Category> listOfData = new ArrayList<>();
        try {
            videoRequestDTO = objectMapper.readValue(videoDTO, VideoRequestDTO.class);
            String[] splitCategory = videoRequestDTO.getCategory().split(",");
            listOfData = getListDataCategory(splitCategory);
            Video video = new Video();
            video.setName(videoRequestDTO.getName());
            video.setDescription(videoRequestDTO.getDescription());
            video.setSecureId(UUID.randomUUID().toString());
            // video.setCategory(listOfData);
            // video.setCategories(categories);
            // video.setGenre(genreService.findById(Long.parseLong(videoRequestDTO.getGenre())).get());

            video.setVideoSaved(fileStorageService.storeFile(file, video.getName()));
            return repo.saveAndFlush(video);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Video saveWithGenreBook(MultipartFile file, String videoDTO){
        ObjectMapper objectMapper = new ObjectMapper();
        VideoRequestDTO videoRequestDTO = new VideoRequestDTO();
        List<GenreBook> listGenreBooks = new ArrayList<>();
        log.info("# data videoDTO : {}", videoDTO);
        try {
            videoRequestDTO = objectMapper.readValue(videoDTO, VideoRequestDTO.class);
            String[] splitCategory = videoRequestDTO.getGenre().split(",");
            listGenreBooks = getListDataGenre(splitCategory);
            Video video = new Video();
            video.setName(videoRequestDTO.getName());
            video.setDescription(videoRequestDTO.getDescription());
            video.setSecureId(UUID.randomUUID().toString());
            video.setCategory(categoryService.findById(Long.parseLong(videoRequestDTO.getCategory())));
            log.info("# before save data listGenreBooks : {}", listGenreBooks);
            video.setGenreBooks(listGenreBooks);

            video.setVideoSaved(fileStorageService.storeFile(file, video.getName()));
            return repo.save(video);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    List<Category> getListDataCategory(String[] arr){
        List<Category> list = new ArrayList<>();
        if(arr.length == 0){
            return null;
        }
        for(int i=0; i<arr.length; i++){
            list.add(categoryService.findById(Long.parseLong(arr[i])));
        }
        return list;
    }

    List<GenreBook> getListDataGenre(String[] arrs){
        List<GenreBook> datas = new ArrayList<>();
        
        if(arrs.length < 0){
            return null;
        }
        for(String arr : arrs){
            Genre genre = genreService.findById(Long.parseLong(arr)).get();

            if(!genre.equals(null)){
                GenreBook gb = new GenreBook();
                gb.setGenreId(genre.getId());
                gb.setGenreName(genre.getGenreName());
                datas.add(gb);
            }
        }
        return datas;
    }
}