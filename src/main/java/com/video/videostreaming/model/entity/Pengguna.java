package com.video.videostreaming.model.entity;

import javax.persistence.*;

import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Pengguna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String secureId;
    private String password;
    private String nama;
    private String alamat;
    private String email;
    private String hp;
    private Boolean isAktif;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "pengguna_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
