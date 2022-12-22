package com.video.videostreaming.service;

import com.video.videostreaming.model.entity.Pengguna;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PenggunaService {
    public Pengguna findById(String id);
    public List<Pengguna> findAll();
    public Pengguna create(Pengguna pengguna);
    public Pengguna edit(Pengguna pengguna);
    public void deleteById(String id);
}
