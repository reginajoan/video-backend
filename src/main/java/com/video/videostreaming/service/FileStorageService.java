package com.video.videostreaming.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    public FileStorageService(Environment env){
        this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir","./src/main/resources/video"))
                .toAbsolutePath().normalize();
        try{
            Files.createDirectories(this.fileStorageLocation);
        }catch (Exception e){
            throw new RuntimeException("Could not create the directory", e);
        }
    }

    private String getfileExtension(String fileName){
        if(fileName == null){
            return fileName;
        }
        String[] fileNameParts = fileName.split("\\.");
        return fileNameParts[fileNameParts.length -1];
    }

    public boolean storeFile(MultipartFile file, String name){
        String fileName = name + "." + getfileExtension(file.getOriginalFilename());

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return true;
        }catch (Exception e){
            log.error("Could not store file " + fileName+ ". Please try again!", e);
            //throw new RuntimeException("Could not store file " + fileName+ ". Please try again!", e);
            return false;
        }
    }

    public boolean deleteVideo(String videoName){
        File fileDir = new File("./src/main/resources/video");
        File[] listOfData = fileDir.listFiles();
        for(File file : listOfData){
            log.info("file : {}", file.getName());
            String name = FilenameUtils.removeExtension(file.getName());
            log.info("name : {}", name);
            if(name.equals(videoName)){
                file.delete();
                return true;
            }
        }
        return false;
    }
}