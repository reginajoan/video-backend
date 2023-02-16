package com.video.videostreaming.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.video.videostreaming.model.entity.Pengguna;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PenggunaRepo extends JpaRepository<Pengguna, Long>  {
    Boolean existsByEmail(String email);

    @Query(value = "SELECT * from Pengguna p where p.secure_id=?1", nativeQuery = true)
    Pengguna findbySecureId(String secureId);
}
