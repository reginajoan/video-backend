package com.video.videostreaming.service.impl;

import com.video.videostreaming.exception.BadRequestException;
import com.video.videostreaming.exception.ResourceNotFoundException;
import com.video.videostreaming.model.entity.Pengguna;
import com.video.videostreaming.model.repository.PenggunaRepo;
import com.video.videostreaming.service.PenggunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PenggunaServiceImpl implements PenggunaService {

    @Autowired
    private PenggunaRepo penggunaRepo;
    @Override
    public Pengguna findById(String id) {
        return penggunaRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pengguna dengan id " + id + " tidak ditemukan"));
    }

    @Override
    public List<Pengguna> findAll() {
        return penggunaRepo.findAll();
    }

    @Override
    public Pengguna create(Pengguna pengguna) {
        if (!StringUtils.hasText(pengguna.getId())) {
            throw new BadRequestException("Username harus diisi");
        }

        if (penggunaRepo.existsById(pengguna.getId())) {
            throw new BadRequestException("Username " + pengguna.getId() + " sudah terdaftar");
        }

        if (!StringUtils.hasText(pengguna.getEmail())) {
            throw new BadRequestException("Email harus diisi");
        }

        if (penggunaRepo.existsByEmail(pengguna.getEmail())) {
            throw new BadRequestException("Email " + pengguna.getEmail() + " sudah terdaftar");
        }

        pengguna.setIsAktif(true);
        return penggunaRepo.save(pengguna);
    }

    @Override
    public Pengguna edit(Pengguna pengguna) {
        if (!StringUtils.hasText(pengguna.getId())) {
            throw new BadRequestException("Username harus diisi");
        }

        if (!StringUtils.hasText(pengguna.getEmail())) {
            throw new BadRequestException("Email harus diisi");
        }

        return penggunaRepo.save(pengguna);
    }

    @Override
    public void deleteById(String id) {
        penggunaRepo.deleteById(id);
    }
}
