package com.video.videostreaming.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.video.videostreaming.dto.VideoRequestDTO;
import com.video.videostreaming.model.entity.Category;
import com.video.videostreaming.model.entity.Video;
import com.video.videostreaming.model.repository.VideoRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class VideoServiceTest {

    @Autowired
    private VideoRepo repo;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GenreService genreService;
    
    @Test
    void testSavedVideo(){
        Video vid = new Video();
        VideoRequestDTO dto = new VideoRequestDTO();
        dto.setName("test");
        dto.setDescription("this is toy");
        dto.setCategory("1,2");
        dto.setGenre("1");

        //vid.setCategories2(null);

        //Set<T> mySet = new HashSet()<>(Arrays.asList(someArray));
    }


    // public Object save(String videoDTO) {
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     VideoRequestDTO videoRequestDTO = new VideoRequestDTO();
    //     List<Category> listOfData = new ArrayList();
    //     try {
    //         Category ct = new Category();
    //         videoRequestDTO = objectMapper.readValue(videoDTO, VideoRequestDTO.class);
    //         String[] splitCategory = videoRequestDTO.getCategory().split(",");
    //         listOfData = getListDataCategory(splitCategory);
    //         Category[] categories = listOfData.toArray(new Category[listOfData.size()]);
    //         Video video = new Video();
    //         video.setName(videoRequestDTO.getName());
    //         video.setDescription(videoRequestDTO.getDescription());
    //         video.setSecureId(UUID.randomUUID().toString());
    //         video.setCategory(listOfData);
    //         video.setCategories(categories);
    //         video.setGenre(genreService.findById(Long.parseLong(videoRequestDTO.getGenre())).get());
    //         //video.setCategories2();
    //         return repo.saveAndFlush(video);
    //     } catch (JsonMappingException e) {
    //         e.printStackTrace();
    //     } catch (JsonProcessingException e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }

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

}
