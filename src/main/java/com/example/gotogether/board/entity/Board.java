package com.example.gotogether.board.entity;

import com.example.gotogether.auth.entity.User;
import com.example.gotogether.board.dto.BoardDTO;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "board")
public class Board {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @NotNull
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private BoardType type;

    @Column(name = "thumbnail")
    @ColumnDefault("''")
    private String thumbnail;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "role")
    private String role;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Builder
    public Board(User user, BoardType type, String thumbnail, String title, String content, String role) {
        this.user = user;
        this.type = type;
        this.thumbnail = thumbnail;
        this.title = title;
        this.content = content;
        this.role = role;
    }

    public void update(BoardDTO.ModifyReqDTO modifyReqDTO) {
        this.thumbnail = modifyReqDTO.getBoardThumbnail();
        this.title = modifyReqDTO.getBoardTitle();
        this.content = modifyReqDTO.getBoardContent();
    }
}
