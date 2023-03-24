package com.example.gotogether.auth.service.Impl;

import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.auth.entity.User;
import com.example.gotogether.auth.repository.UserRepository;
import com.example.gotogether.auth.service.AdminService;
import com.example.gotogether.global.response.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.gotogether.global.config.PageSizeConfig.User_List_Size;

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
    public ResponseEntity<?> findUserList(int page) {
        try {
            PageRequest pageable = PageRequest.of(page - 1, User_List_Size);
            Page<UserDTO.UserListDto> userList = userRepository.findAll(pageable)
                    .map(UserDTO.UserListDto::new);
            if (userList.getTotalElements() < 1) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(new PageResponseDTO(userList), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> findUser(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
            return new ResponseEntity<>(new UserDTO.UserDetailsForAdmin(user), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateUserInfo(Long userId, UserDTO.PatchUserByAdminReqDTO dto) {
        try {
            User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
            user.setName(dto.getUserName());
            user.setEmail(dto.getUserEmail());
            user.setPhoneNumber(dto.getUserPhoneNumber());
            user.setBirthday(dto.getUserBirthday());
            user.setGender(dto.getUserGender());
            user.setType(dto.getUserType());
            user.setRole(dto.getUserRole());
            user.setDeleteCheck(dto.getDeleteCheck());
            user.setSns(dto.getSns());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
