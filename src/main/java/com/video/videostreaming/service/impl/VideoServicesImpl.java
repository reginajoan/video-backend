package com.video.videostreaming.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.video.videostreaming.model.entity.GenreMovie;
import com.video.videostreaming.utils.VIDSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.video.videostreaming.dto.VideoRequestDTO;
import com.video.videostreaming.exception.VideoNotFoundException;
import com.video.videostreaming.model.entity.Category;
import com.video.videostreaming.model.entity.Genre;
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
    public Page<String> getAllVideoNames(Pageable pageable) {
        return repo.getAllEntryNames(pageable);
    }

    @Override
    public Page<Video> findAll(Pageable pageable) {
        return repo.findAll(pageable);
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
        List<GenreMovie> listGenreMovies = new ArrayList<>();
        log.info("# data videoDTO : {}", videoDTO);
        log.info("# data file : {}", file);
        try {
            videoRequestDTO = objectMapper.readValue(videoDTO, VideoRequestDTO.class);
            String[] splitCategory = videoRequestDTO.getGenre().split(",");
            listGenreMovies = getListDataGenre(splitCategory);
            Video video = new Video();
            video.setName(videoRequestDTO.getName());
            video.setDescription(videoRequestDTO.getDescription());
            video.setSecureId(UUID.randomUUID().toString());
            video.setCategory(categoryService.findById(Long.parseLong(videoRequestDTO.getCategory())));
            log.info("# before save data listGenreBooks : {}", listGenreMovies);
            video.setGenreBooks(listGenreMovies);
            log.info("# file : {}", file);
            log.info("# fileName : {}",video.getName());
            video.setVideoSaved(fileStorageService.storeFile(file, video.getName()));
            return repo.save(video);
        } catch (JsonMappingException e) {
            e.printStackTrace();
            log.info("JsonMappingException e : {}",e.getMessage());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.info("JsonProcessingException e : {}", e.getMessage());
        }
        return null;
    }

    @Override
    public Page<Video> findSpecification(String name, String description, Pageable pageable) {
        return repo.findAll(VIDSpec.findCriteria(name, description), pageable);
    }

    @Override
    public Video updateBook(MultipartFile file, String videoDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        VideoRequestDTO videoRequestDTO = new VideoRequestDTO();
        List<GenreMovie> listGenreMovies = new ArrayList<>();
        log.info("# data videoDTO : {}", videoDTO);
        log.info("# data file : {}", file);
        Video video = new Video();
        try {
            videoRequestDTO = objectMapper.readValue(videoDTO, VideoRequestDTO.class);
            String[] splitCategory = videoRequestDTO.getGenre().split(",");
            listGenreMovies = getListDataGenre(splitCategory);

            video.setName(videoRequestDTO.getName());
            video.setDescription(videoRequestDTO.getDescription());
            video.setSecureId(UUID.randomUUID().toString());
            video.setCategory(categoryService.findById(Long.parseLong(videoRequestDTO.getCategory())));
            log.info("# before save data listGenreBooks : {}", listGenreMovies);
            video.setGenreBooks(listGenreMovies);
            log.info("# file : {}", file);
            log.info("# fileName : {}",video.getName());
            video.setVideoSaved(fileStorageService.storeFile(file, video.getName()));

            if(!repo.existsByName(video.getName())){
                video = repo.save(video);
            }
            return video;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            log.info("JsonMappingException e : {}",e.getMessage());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.info("JsonProcessingException e : {}", e.getMessage());
        }
        return video;
    }

    @Override
    public List<Video> findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCaseOrderByName(String name, String description) {
        return repo.findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCaseOrderByName(name, description);
    }

    @Override
    public List<Video> findTop20ByNameContainingIgnoreCaseOrderByNameAsc(String name) {
        return repo.findTop20ByNameContainingIgnoreCaseOrderByNameAsc(name);
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

    List<GenreMovie> getListDataGenre(String[] arrs){
        List<GenreMovie> datas = new ArrayList<>();
        
        if(arrs.length < 0){
            return null;
        }
        for(String arr : arrs){

            List<Long> ids = genreService.findIdAll();
            for(Long id : ids){
                if(Long.parseLong(arr) == id){
                    Genre genre = genreService.findById((Long)id).get();
                    if(!genre.equals(null)){
                        GenreMovie gm = new GenreMovie();
                        gm.setGenreId(genre.getId());
                        gm.setGenreName(genre.getGenreName());
                        datas.add(gm);
                    }
                }
            }
        }
        return datas;
    }
}