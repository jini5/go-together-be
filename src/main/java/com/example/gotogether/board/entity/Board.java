package com.example.gotogether.board.entity;

import com.example.gotogether.auth.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "board")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Board {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
//    private List<Comment> comments = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @Column(name="type")
    private String type;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime created_date;

    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updated_date;




}
