package com.video.videostreaming.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.video.videostreaming.model.entity.Pengguna;
import com.video.videostreaming.model.repository.PenggunaRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    PenggunaRepo penggunaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Pengguna pengguna = penggunaRepository.findById(username)
        .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " tidak ditemukan"));
        return UserDetailsImpl.build(pengguna);
    }
    
}
