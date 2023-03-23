package com.example.gotogether.auth.service;


import com.example.gotogether.auth.dto.TokenDTO;
import org.springframework.http.ResponseEntity;

public interface TokenService {

    ResponseEntity<?> logout(String header, TokenDTO.RefreshTokenReqDTO refreshTokenReqDTO);

    ResponseEntity<?> validateRefreshToken(String refreshToken);

    boolean checkToken(String token);
}
