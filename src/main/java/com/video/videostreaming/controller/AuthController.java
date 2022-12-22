package com.video.videostreaming.controller;

import com.video.videostreaming.dto.JwtResponse;
import com.video.videostreaming.dto.LoginRequest;
import com.video.videostreaming.dto.SignupRequest;
import com.video.videostreaming.model.entity.Pengguna;

import com.video.videostreaming.security.jwt.JwtUtils;
import com.video.videostreaming.security.service.UserDetailsImpl;
import com.video.videostreaming.service.PenggunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PenggunaService penggunaService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok().body(new JwtResponse(token, principal.getUsername(), principal.getEmail()));
    }

    @PostMapping("/signup")
    public Pengguna signup(@RequestBody SignupRequest request) {
        Pengguna pengguna = new Pengguna();
        pengguna.setId(request.getUsername());
        pengguna.setEmail(request.getEmail());
        pengguna.setPassword(passwordEncoder.encode(request.getPassword()));
        pengguna.setNama(request.getNama());
        pengguna.setRoles("user");
        Pengguna created = penggunaService.create(pengguna);
        return created;
    }
}
