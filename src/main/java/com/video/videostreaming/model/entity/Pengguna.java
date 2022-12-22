package com.video.videostreaming.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Pengguna {
    @Id
    private String id;
    private String password;
    private String nama;
    private String alamat;
    private String email;
    private String hp;
    private String roles;
    private Boolean isAktif;
}
