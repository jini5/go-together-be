package com.example.gotogether.auth.service;


import com.example.gotogether.auth.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> signup(UserDTO.SignupReqDTO signupReqDTO);

    ResponseEntity<?> login(UserDTO.LoginReqDTO loginReqDTO);

    ResponseEntity<?> checkUserInfo(UserDTO.UserAccessDTO userAccessDTO);

    ResponseEntity<?> updateUser(UserDTO.UserAccessDTO userAccessDTO, UserDTO.PatchUserReqDTO patchUserReqDTO);

    ResponseEntity<?> deleteUser(UserDTO.UserAccessDTO userAccessDTO, UserDTO.DeleteUserReqDTO deleteUserReqDTO);

    ResponseEntity<?> emailDuplicationCheck(String email);

    String makePassword();

    ResponseEntity<?> sendPwEmail(String userEmail);

    ResponseEntity<?> saveUserType(UserDTO.UserAccessDTO userAccessDTO,UserDTO.UserType userType);
}
