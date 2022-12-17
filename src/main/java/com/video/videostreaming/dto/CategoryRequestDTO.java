package com.video.videostreaming.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CategoryRequestDTO implements Serializable {
    
    private static final long serialVersionUID=1L;
    
    private String category;

    private String description;

    // private String video;

}
