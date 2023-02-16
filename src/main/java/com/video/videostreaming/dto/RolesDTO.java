package com.video.videostreaming.dto;

import com.video.videostreaming.model.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class RolesDTO {
    private Set<Role> roles;
}
