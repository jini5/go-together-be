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
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phone;

    @Column(name = "birthday")
    private String birth;

    @Column(name = "gender")
    private String gender;

    @Column(name = "type")
    private String type;

    @Column(name = "delete_check")
    private String deleteCheck;

    @Column(name = "role", nullable = false)
    private String role;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

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
