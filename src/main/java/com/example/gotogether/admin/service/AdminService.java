package com.example.gotogether.admin.service;

import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.global.response.ResponseDTO;
import org.springframework.data.domain.Pageable;

public interface AdminService {

    ResponseDTO<?> setUserToAdmin(UserDTO.EmailOnly dto);

    ResponseDTO<?> setAdminToUser(UserDTO.EmailOnly dto);

    ResponseDTO<?> findUserList(Pageable pageable);
}
