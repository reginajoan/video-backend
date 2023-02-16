package com.video.videostreaming.controller;

import com.video.videostreaming.dto.JwtResponse;
import com.video.videostreaming.dto.LoginRequest;
import com.video.videostreaming.dto.RefreshTokenRequest;
import com.video.videostreaming.dto.SignupRequest;
import com.video.videostreaming.exception.BadRequestException;
import com.video.videostreaming.helper.ERole;
import com.video.videostreaming.model.entity.Pengguna;

import com.video.videostreaming.model.entity.Role;
import com.video.videostreaming.model.repository.RoleRepo;
import com.video.videostreaming.security.jwt.JwtUtils;
import com.video.videostreaming.security.service.UserDetailsImpl;
import com.video.videostreaming.security.service.UserDetailsServiceImpl;
import com.video.videostreaming.service.PenggunaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import java.util.HashSet;
import java.util.Set;

@Slf4j
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
    RoleRepo roleService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest request) {
        log.info("request signin controller : {}", request);
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);
        String refreshToken = jwtUtils.generateRefresJwtToken(authentication);
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok().body(new JwtResponse(token, refreshToken, principal.getUsername(), principal.getEmail()));
    }

    @PostMapping("/signup")
    public Pengguna signup(@RequestBody SignupRequest request) {
        Pengguna pengguna = new Pengguna();
        pengguna.setSecureId(request.getUsername());
        pengguna.setEmail(request.getEmail());
        pengguna.setPassword(passwordEncoder.encode(request.getPassword()));
        pengguna.setNama(request.getNama());
        Set<String> strRoles = request.getRole();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role ->{
                switch(role) {
                    case "ADMIN":
                        Role adminRole = roleService.findByRole(ERole.ROLE_ADMIN);
                        log.info("admiRole : {}",adminRole);
                        roles.add(adminRole);
                        break;
                    case "MODERATOR":
                        Role modRole = roleService.findByRole(ERole.ROLE_MODERATOR);
                        log.info("modRole : {}",modRole);
                        roles.add(modRole);
                        break;
                    case "USER":
                        Role userRole = roleService.findByRole(ERole.ROLE_USER);
                        log.info("userRole : {}",userRole);
                        roles.add(userRole);
                        break;
                    default:
                        new BadRequestException("Role not found");
                }
        });
        pengguna.setRoles(roles);
        Pengguna created = penggunaService.create(pengguna);
        return created;
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        String token = request.getRefreshToken();
        boolean valid = jwtUtils.validateJwtToken(token);
        if (!valid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String username = jwtUtils.getUserNameFromJwtToken(token);
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetailsServiceImpl.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailsImpl, null,
                userDetailsImpl.getAuthorities());
        String newToken = jwtUtils.generateJwtToken(authentication);
        String refreshToken = jwtUtils.generateRefresJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(newToken, refreshToken, username, userDetailsImpl.getEmail()));
    }
}
