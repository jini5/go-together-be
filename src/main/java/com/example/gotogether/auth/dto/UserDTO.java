package com.example.gotogether.auth.dto;

import com.example.gotogether.auth.entity.User;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

public class UserDTO {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ApiModel(value = "로그인")
    public static class LoginReqDTO {

        @ApiModelProperty(value = "이메일 ", required = true)
        private String email;

        @ApiModelProperty(value = "비밀번호 ", required = true)
        private String password;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ApiModel(value = "토큰에 담길 정보")
    public static class UserAccessDTO {
        @ApiModelProperty(value = "이메일 ", required = true)
        private String email;

        @ApiModelProperty(value = "권한 ", required = true)
        private String role;

        public UserAccessDTO(Claims claims) {
            this.email = claims.get("email", String.class);
            this.role = claims.get("role", String.class);
        }

        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.singleton(new SimpleGrantedAuthority(this.role));
        }

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    @ApiModel(value = "회원가입")
    public static class SignupReqDTO {
        @ApiModelProperty(value = "이름", required = true)
        private String userName;
        @ApiModelProperty(value = "이메일", required = true)
        private String userEmail;
        @ApiModelProperty(value = "비밀번호", required = true)
        private String userPassword;
        @ApiModelProperty(value = "비밀번호 확인", required = true)
        private String passwordConfirmation;
        @ApiModelProperty(value = "전화번호", required = true)
        private String userPhoneNumber;
        @ApiModelProperty(value = "생년월일", required = true)
        private String userBirth;
        @ApiModelProperty(value = "성별", required = true)
        private String userGender;
        @ApiModelProperty(value = "sns 로그인 여부", required = true)
        private String sns;



        public User toEntity() {

            return User.builder()
                    .name(userName)
                    .email(userEmail)
                    .password(userPassword)
                    .phoneNumber(userPhoneNumber)
                    .birthday(userBirth)
                    .gender(userGender)
                    .sns(sns)
                    .role("ROLE_USER")
                    .build();
        }

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @ApiModel(value = "회원정보수정 입력")
    public static class PatchUserReqDTO {

        @ApiModelProperty(value = "기존 비밀번호", required = true)
        private String userPassword;
        @ApiModelProperty(value = "새로운 비밀번호")
        private String changePassword;
        @ApiModelProperty(value = "새로운 비밀번호 재입력")
        private String passwordConfirmation;
        @ApiModelProperty(value = "전화번호",required = true)
        private String userPhoneNumber;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @ApiModel(value = "회원정보수정 출력")
    public static class PatchUserResDTO {

        @ApiModelProperty(value = "이메일")
        private String email;
        @ApiModelProperty(value = "비밀번호")
        private String password;
        @ApiModelProperty(value = "이름")
        private String name;
        @ApiModelProperty(value = "생년월일")
        private String birth;
        @ApiModelProperty(value = "전화번호")
        private String phone;


        public PatchUserResDTO(User user) {
            this.email = user.getEmail();
            this.password = user.getPassword();
            this.name = user.getName();
            this.birth = user.getBirthday();
            this.phone = user.getPhoneNumber();
        }
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @ApiModel(value = "회원 탈퇴")
    public static class DeleteUserReqDTO {

        @ApiModelProperty(value = "비밀번호 ", required = true)
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @ApiModel(value = "이메일만 사용하는 dto")
    public static class EmailOnly {

        @ApiModelProperty(value = "이메일 ", required = true)
        private String email;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @ApiModel(value = "회원리스트 출력")
    public static class UserListDto{
        private Long userId;
        private String email;
        private String name;

        public UserListDto(User user){
            this.userId=user.getUserId();
            this.email=user.getEmail();
            this.name=user.getName();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @ApiModel(value = "사용자 여행 유형 dto")
    public static class UserType {

        @ApiModelProperty(value = "사용자 여행 유형 ", required = true)
        private String userType;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class UserDetailsForAdmin{
        private Long userId;
        private String name;
        private String email;
        private String phoneNumber;
        private String birthday;
        private String gender;
        private String type;
        private String sns;
        private String deleteCheck;
        private String role;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;

        public UserDetailsForAdmin(User user) {
            this.userId = user.getUserId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.phoneNumber = user.getPhoneNumber();
            this.birthday = user.getBirthday();
            this.gender = user.getGender();
            this.type = user.getType();
            this.sns = user.getSns();
            this.deleteCheck = user.getDeleteCheck();
            this.role = user.getRole();
            this.createdDate = user.getCreatedDate();
            this.updatedDate = user.getUpdatedDate();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @ApiModel(value = "관리자의 회원정보수정 입력",description = "변경 하지 않는다면 기존 정보 입력 요망.")
    public static class PatchUserByAdminReqDTO {
        @ApiModelProperty(value = "사용자 이름 입력.",required = true)
        private String userName;
        @ApiModelProperty(value = "사용자 이메일 입력",required = true)
        private String userEmail;
        @ApiModelProperty(value = "사용자 전화번호 입력",required = true)
        private String userPhoneNumber;
        @ApiModelProperty(value = "사용자 생년월일 입력",required = true)
        private String userBirthday;
        @ApiModelProperty(value = "사용자 성별 입력\n male or female",required = true)
        private String userGender;
        @ApiModelProperty(value = "사용자 여행 유형 입력",required = true)
        private String userType;
        @ApiModelProperty(value = "사용자 권한 입력\n ROLE_ADMIN or ROLE_USER",required = true)
        private String userRole;
        @ApiModelProperty(value = "사용자 탈퇴여부 입력\n available or withdraw",required = true)
        private String deleteCheck;
        @ApiModelProperty(value = "소셜 로그인 여부 입력",required = true)
        private String sns;
    }
}

