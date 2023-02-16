package com.video.videostreaming.service;

import com.video.videostreaming.model.entity.Pengguna;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PenggunaService {
    Pengguna findById(String id);
    List<Pengguna> findAll();
    Pengguna create(Pengguna pengguna);
    Pengguna edit(Pengguna pengguna);
    void deleteById(Long id);
}
