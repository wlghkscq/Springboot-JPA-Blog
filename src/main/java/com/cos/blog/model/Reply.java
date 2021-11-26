package com.cos.blog.model;

import com.cos.blog.dto.ReplySaveRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import java.sql.Timestamp;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reply { // 댓글

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라감 ( AUTO 증가 )
    // IDENTITY 는 Oracle -> 시퀀스번호   MySQL -> AUTO INCREMENT
    private int id;

    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne // Many = Reply 여러개의 댓글은 One = BOARD 하나의 게시물에 달수있다.
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne // Many = Reply 여러개의 댓글은 One = User 한명의 유저가 달수있다.
    @JoinColumn(name = "userId")
    private User user;

    @CreationTimestamp
    private Timestamp createdDate;



}
