//package com.example.gotogether.board.entity;
//
//import com.example.gotogether.auth.entity.User;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "comment")
//@Getter
//@Setter
//@NoArgsConstructor
//@DynamicInsert
//@DynamicUpdate
//public class Comment {
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "board_id")
//    private Board board;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "comment_id")
//    private Long commentId;
//
//    @Column(name="content")
//    private String content;
//
//    @CreationTimestamp
//    @Column(name = "created_date")
//    private LocalDateTime created_date;
//
//    @UpdateTimestamp
//    @Column(name = "updated_date")
//    private LocalDateTime updated_date;
//
//
//}
