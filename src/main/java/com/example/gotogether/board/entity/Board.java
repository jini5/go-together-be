package com.example.gotogether.board.entity;

import com.example.gotogether.auth.entity.User;
import com.example.gotogether.board.dto.BoardDTO;
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

//    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
//    private List<Comment> comments = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

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

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Builder
    public Board(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void update(BoardDTO.ModifyReqDTO modifyReqDTO) {
        this.title = modifyReqDTO.getBoardTitle();
        this.content = modifyReqDTO.getBoardContent();
    }
}
