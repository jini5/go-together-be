package com.example.gotogether.auth.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "grouping")
public class Grouping {
    @Id
    @Column(name = "user_type")
    private String userType;

    @Column(name = "group_name")
    private String group;

    public Grouping(String userType, String group) {
        this.userType = userType;
        this.group = group;
    }
}
