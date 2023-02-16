package com.video.videostreaming.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class SignupRequest implements Serializable {
    private String username;
    private String password;
    private String email;
    private String nama;
    private Set<String> role;
}
