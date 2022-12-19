package com.video.videostreaming.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.video.videostreaming.service.impl.ConnectionService;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    
    @Autowired
    private ConnectionService connectionService;

    @GetMapping("/all")
    public Map<String, String> getData(){
        return connectionService.view();
    }
}
