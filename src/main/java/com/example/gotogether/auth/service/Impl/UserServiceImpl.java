package com.example.gotogether.auth.service.Impl;

import com.example.gotogether.auth.dto.MailDTO;
import com.example.gotogether.auth.dto.TokenDTO;
import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.auth.entity.Grouping;
import com.example.gotogether.auth.entity.User;
import com.example.gotogether.auth.jwt.JwtProvider;
import com.example.gotogether.auth.repository.GroupingRepository;
import com.example.gotogether.auth.repository.RedisTemplateRepository;
import com.example.gotogether.auth.repository.UserRepository;
import com.example.gotogether.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    private final GroupingRepository groupingRepository;

    @Override
    public ResponseEntity<?> signup(UserDTO.SignupReqDTO signupReqDTO) {
        if (userRepository.findByEmail(signupReqDTO.getUserEmail()).isPresent() ||
                !signupReqDTO.getUserPassword().equals(signupReqDTO.getPasswordConfirmation())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String encodingPassword = encodingPassword(signupReqDTO.getUserPassword());
        signupReqDTO.setUserPassword(encodingPassword);
        userRepository.save(signupReqDTO.toEntity());
        return new ResponseEntity<>(signupReqDTO.toString(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> login(UserDTO.LoginReqDTO loginReqDTO) {
        try {
            User user = userRepository.findByEmail(loginReqDTO.getEmail())
                    .orElseThrow(IllegalArgumentException::new);
            if (!withDrawCheck(user)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            passwordMustBeSame(loginReqDTO.getPassword(), user.getPassword());
            TokenDTO tokenDTO = jwtProvider.makeJwtToken(user);
            redisTemplateRepository.setDataExpire(tokenDTO.getRefreshToken(), user.getEmail(), jwtProvider.getExpiration(tokenDTO.getRefreshToken()));
            return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> checkUserInfo(UserDTO.UserAccessDTO userAccessDTO) {
        try {
            if (userAccessDTO != null) {
                User user = userRepository.findByEmail(userAccessDTO.getEmail())
                        .orElseThrow(IllegalArgumentException::new);
                return new ResponseEntity<>(new UserDTO.PatchUserResDTO(user), HttpStatus.OK);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateUser(UserDTO.UserAccessDTO userAccessDTO, UserDTO.PatchUserReqDTO patchUserReqDTO) {
        try {
            User user = userRepository.findByEmail(userAccessDTO.getEmail()).orElseThrow(IllegalArgumentException::new);

            passwordMustBeSame(patchUserReqDTO.getUserPassword(), user.getPassword());

            if (!patchUserReqDTO.getChangePassword().equals(patchUserReqDTO.getPasswordConfirmation())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            patchUserReqDTO.setChangePassword(encodingPassword(patchUserReqDTO.getChangePassword()));

            user.update(patchUserReqDTO.getChangePassword(), patchUserReqDTO.getUserPhoneNumber());
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteUser(UserDTO.UserAccessDTO userAccessDTO, UserDTO.DeleteUserReqDTO deleteUserReqDTO) {
        try {
            User user = userRepository.findByEmail(userAccessDTO.getEmail())
                    .orElseThrow(IllegalArgumentException::new);
            passwordMustBeSame(deleteUserReqDTO.getPassword(), user.getPassword());
            user.setDeleteCheck("withdraw");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<?> emailDuplicationCheck(String email) {
        if (userRepository.findByEmail(email).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private String encodingPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean withDrawCheck(User user) {
        return user.getDeleteCheck().equals("available");
    }

    private void passwordMustBeSame(String requestPassword, String password) {
        if (!passwordEncoder.matches(requestPassword, password)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String makePassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String pwd = "";

        /* 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 조합 */
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            pwd += charSet[idx];
        }

        return pwd;
    }


    @Override
    @Transactional
    public ResponseEntity<?> sendPwEmail(String userEmail) {

        if (!userRepository.existsByEmail(userEmail)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String tmpPassword = makePassword();
        String encryptPassword = encodingPassword(tmpPassword);
        User user = userRepository.findByEmail(userEmail).get();
        user.updatePassword(encryptPassword);

        MailDTO mailDTO = createMail(tmpPassword, userEmail);
        sendMail(mailDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> saveUserType(UserDTO.UserAccessDTO userAccessDTO, UserDTO.UserType userType) {
        try {
            User user = userRepository.findByEmail(userAccessDTO.getEmail()).orElseThrow(IllegalArgumentException::new);
            Grouping grouping = groupingRepository.findById(userType.getUserType()).orElseThrow(NoSuchElementException::new);
            user.setType(grouping);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private final JavaMailSender mailSender;
    private static final String title = "고투게더 임시 비밀번호 안내 이메일입니다.";
    private static final String message = "안녕하세요. 고투게더 임시 비밀번호 안내 메일입니다. "
            + "\n" + "회원님의 임시 비밀번호는 아래와 같습니다. 로그인 후 반드시 비밀번호를 변경해주세요." + "\n";
    private static final String fromAddress = "Go_TOGETHER";

    public MailDTO createMail(String tmpPassword, String userEmail) {

        MailDTO mailDto = MailDTO.builder()
                .toAddress(userEmail)
                .title(title)
                .message(message + tmpPassword)
                .fromAddress(fromAddress)
                .build();

        return mailDto;
    }

    public void sendMail(MailDTO mailDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailDto.getToAddress());
        mailMessage.setSubject(mailDto.getTitle());
        mailMessage.setText(mailDto.getMessage());
        mailMessage.setFrom(mailDto.getFromAddress());
        mailMessage.setReplyTo(mailDto.getFromAddress());

        mailSender.send(mailMessage);

    }
}
