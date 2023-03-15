package com.example.gotogether.auth.service.Impl;

import com.example.gotogether.auth.dto.TokenDTO;
import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.auth.entity.User;
import com.example.gotogether.auth.jwt.JwtProvider;
import com.example.gotogether.auth.repository.RedisTemplateRepository;
import com.example.gotogether.auth.repository.UserRepository;
import com.example.gotogether.auth.service.UserService;
import com.example.gotogether.global.response.ResponseDTO;
import com.example.gotogether.global.response.ResponseStatusCustomCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RedisTemplateRepository redisTemplateRepository;

    @Override
    public ResponseDTO<?> signup(UserDTO.SignupReqDTO signupReqDTO) {
        if (userRepository.findByEmail(signupReqDTO.getEmail()).isPresent()){
            return new ResponseDTO<>(HttpStatus.BAD_REQUEST, null, "이미 존재하는 회원입니다.");
        }
        if (!signupReqDTO.getPassword().equals(signupReqDTO.getPasswordConfirmation())){
            return new ResponseDTO<>(HttpStatus.BAD_REQUEST,null,"비밀번호가 일치하지 않습니다.");
        }
            String encodingPassword = encodingPassword(signupReqDTO.getPassword());
            signupReqDTO.setPassword(encodingPassword);
            userRepository.save(signupReqDTO.toEntity());
            return new ResponseDTO<>(signupReqDTO.toString());
    }

    @Override
    public ResponseDTO<?> login(UserDTO.LoginReqDTO loginReqDTO) {
        try {
            User user = userRepository.findByEmail(loginReqDTO.getEmail())
                    .orElseThrow(IllegalArgumentException::new);
            if (withDrawCheck(user)) {
                return new ResponseDTO<>(HttpStatus.BAD_REQUEST, null, "탈퇴한 회원입니다.");
            }
            passwordMustBeSame(loginReqDTO.getPassword(), user.getPassword());
            TokenDTO tokenDTO = jwtProvider.makeJwtToken(user);
            redisTemplateRepository.setDataExpire(tokenDTO.getRefreshToken(), user.getEmail(), jwtProvider.getExpiration(tokenDTO.getRefreshToken()));
            if (user.getRole().equals("ROLE_ADMIN")) {
                return new ResponseDTO<>(HttpStatus.OK, ResponseStatusCustomCode.ADMIN.getCode(), "관리자 권한 입니다.", tokenDTO);
            }
            return new ResponseDTO<>(tokenDTO);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return new ResponseDTO<>(HttpStatus.NOT_FOUND, null, "로그인에 실패하였습니다.");
        }
    }

    @Override
    public ResponseDTO<?> checkUserInfo(UserDTO.UserAccessDTO userAccessDTO) {
        try {
            if (userAccessDTO != null) {
                User user = userRepository.findByEmail(userAccessDTO.getEmail())
                        .orElseThrow(IllegalArgumentException::new);
                return new ResponseDTO<>(new UserDTO.PatchUserResDTO(user));
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            return new ResponseDTO<>(HttpStatus.NOT_FOUND, "로그인 정보가 없습니다.");
        }
    }

    @Override
    @Transactional
    public ResponseDTO<?> updateUser(UserDTO.UserAccessDTO userAccessDTO, UserDTO.PatchUserReqDTO patchUserReqDTO) {
        try {
            User user = userRepository.findByEmail(userAccessDTO.getEmail())
                    .orElseThrow(IllegalArgumentException::new);

            passwordMustBeSame(patchUserReqDTO.getOldPassword(), user.getPassword());
            patchUserReqDTO.setNewPassword(encodingPassword(patchUserReqDTO.getNewPassword()));

            user.update(patchUserReqDTO.getNewPassword(), patchUserReqDTO.getPhone());

            return new ResponseDTO<>(HttpStatus.OK, patchUserReqDTO, "회원정보 수정 성공하였습니다.");
        } catch (IllegalArgumentException e) {
            return new ResponseDTO<>(HttpStatus.UNAUTHORIZED, "기존 비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    @Transactional
    public ResponseDTO<?> deleteUser(UserDTO.UserAccessDTO userAccessDTO, UserDTO.DeleteUserReqDTO deleteUserReqDTO) {
        try {
            User user = userRepository.findByEmail(userAccessDTO.getEmail())
                    .orElseThrow(IllegalArgumentException::new);

            passwordMustBeSame(deleteUserReqDTO.getPassword(), user.getPassword());
            user.delete("withdraw");

            return new ResponseDTO<>(HttpStatus.OK, user, "회원 탈퇴 성공.");
        } catch (IllegalArgumentException e) {
            return new ResponseDTO<>(HttpStatus.UNAUTHORIZED, "기존 비밀번호가 일치하지 않습니다.");
        }
    }

    private String encodingPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean withDrawCheck(User user) {
        return user.getDeleteCheck() != null;
    }

    private void passwordMustBeSame(String requestPassword, String password) {
        if (!passwordEncoder.matches(requestPassword, password)) {
            throw new IllegalArgumentException();
        }
    }
}
