package com.example.gotogether.auth.service;


import com.example.gotogether.auth.dto.TokenDTO;
import com.example.gotogether.global.response.ResponseDTO;

public interface TokenService {

    ResponseDTO<?> logout(String header, TokenDTO.RefreshTokenReqDTO refreshTokenReqDTO);

    ResponseDTO<?> validateRefreshToken(String refreshToken);

    boolean checkToken(String token);
}
