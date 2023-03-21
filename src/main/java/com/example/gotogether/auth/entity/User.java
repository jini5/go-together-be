package com.example.gotogether.auth.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
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

    //후에 수정
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
