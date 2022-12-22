package com.video.videostreaming.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.video.videostreaming.model.entity.Pengguna;
import org.springframework.stereotype.Repository;

@Repository
public interface PenggunaRepo extends JpaRepository<Pengguna, String>  {
    Boolean existsByEmail(String email);
}
