package com.example.gotogether.auth.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "user_name")
    private String name;
    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_phone_number")
    private String phone;

    @Column(name = "user_birthday")
    private String birth;

    @Column(name = "user_type")
    private String type;

    @Column(name = "delete_check")
    private String deleteCheck;

    @Column(name = "user_role", nullable = false)
    private String role;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime created_date;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updated_date;

    public void update(String password, String phone) {
        this.password = password;
        this.phone = phone;
    }

    public void delete(String withdraw) {
        this.deleteCheck = withdraw;
    }

    @Builder
    public User(String email, String password, String name, String birth, String phone, String deleteCheck, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.deleteCheck = deleteCheck;
        this.role = role;
    }
}
