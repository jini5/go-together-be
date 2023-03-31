package com.example.gotogether.board.repository;

import com.example.gotogether.board.entity.Board;
import com.example.gotogether.board.entity.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByType(BoardType type, Pageable pageable);

    Page<Board> findByTypeAndTitleContaining(BoardType type, String keyword, Pageable pageable);
}
