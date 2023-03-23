package com.example.gotogether.admin.service;

import com.example.gotogether.auth.dto.UserDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AdminService {

    ResponseEntity<?> setUserToAdmin(UserDTO.EmailOnly dto);

    ResponseEntity<?> setAdminToUser(UserDTO.EmailOnly dto);

    ResponseEntity<?> findUserList(Pageable pageable);
}
