package com.video.videostreaming.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.video.videostreaming.security.service.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtils {

    @Value("${jwt.refreshExpirationMs}")
    private int refreshJwtExpirationMs;
    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public boolean validateJwtToken(String authToken){
        log.info("package JwtUtils authToken : {}", authToken);
        try {
            Jwts.parser().setSigningKey(jwtSecret)
                        .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}",e.getMessage());
        } catch (MalformedJwtException e){
            log.error("Invalid JWT token: {}",e.getMessage());
        } catch (ExpiredJwtException e){
            log.error("JWT token expired: {}",e.getMessage());
        } catch (UnsupportedJwtException e){
            log.error("JWT token unsupported: {}",e.getMessage());
        } catch (IllegalArgumentException e){
            log.error("JWT claims string is empty: {}",e.getMessage());
        }
        return false;
    }

    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("username",principal.getUsername());
        return Jwts.builder().setSubject((principal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateRefresJwtToken(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder().setSubject((principal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + refreshJwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
    }
}
