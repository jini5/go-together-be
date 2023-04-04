package com.example.gotogether.auth.dto;

import com.example.gotogether.auth.entity.Grouping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {

    private String userType;
    private String group;

    public GroupDTO(Grouping grouping) {
        this.userType = grouping.getUserType();
        this.group = grouping.getGroup();
    }
}
