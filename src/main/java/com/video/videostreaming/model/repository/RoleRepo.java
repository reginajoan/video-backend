package com.video.videostreaming.model.repository;

import com.video.videostreaming.helper.ERole;
import com.video.videostreaming.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByRole(ERole role);
    //Optional<Object> saveAll(Set<ERole> roles);
}
