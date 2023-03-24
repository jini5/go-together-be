package com.example.gotogether.auth.service.Impl;

import com.example.gotogether.auth.service.AdminService;
import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.auth.entity.User;
import com.example.gotogether.auth.repository.UserRepository;
import com.example.gotogether.global.response.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ResponseEntity<?> setUserToAdmin(String email) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
            user.setRole("ROLE_ADMIN");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> setAdminToUser(String email) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
            user.setRole("ROLE_USER");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> findUserList(Pageable pageable) {
        try {
            Page<UserDTO.UserListDto> userList = userRepository.findAll(pageable)
                    .map(UserDTO.UserListDto::new);
            if (userList.getTotalElements()<1){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(new PageResponseDTO(userList),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
