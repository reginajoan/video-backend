package com.video.videostreaming.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data
public class ResponseData {

    private boolean status;

    private List<String> message = new ArrayList<>();

    private Object payload;

}
