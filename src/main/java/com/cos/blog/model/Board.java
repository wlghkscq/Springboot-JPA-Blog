package com.cos.blog.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id // PK 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량데이터 사용시 씀
    private String content; // 섬머노트 라이브러리 <html>태그가 섞여서 디자인 됨. -> 글자용량 굉장히 커짐

    private int count; // 조회수

    // Board = Many , User = One 한명의 User는 여러개의 Board (게시글) 작성할수있다.
//    Fetch Type 은 JPA 가 하나의 Entity 를 조회할 때, 연관관계에 있는 객체들을 어떻게 가져올 것이냐를 나타내는 설정값입니다.
    @ManyToOne(fetch = FetchType.EAGER) // 연관 관계에 있는 Entity 들 모두 가져온다 → Eager 전략
    @JoinColumn(name = "userId") // userId라는 이름으로 FK로 join해서 컬럼을 생성
    private User user; // db는 오브젝트를 저장 x -> FK, 자바는 오브젝트(User user)를 저장가능 -> JPA 기능

    // One = Board 하나의 게시물은 Many = Reply 여러개의 댓글을 가질수있다.
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER,  cascade = CascadeType.REMOVE) // mappedBy 연관관계 주인이 아니다 (난 FK가 아니다) DB에 컬럼을 생성하지 마라
    // board 를 select 할때 Join 문을 통해서 값을 얻기위해서 필요함
    @JsonIgnoreProperties({"board"}) // 무한참조 방지
    @OrderBy("id desc") // 내림차순 = 최신순 정렬
    private List<Reply> replys;


    @CreationTimestamp // 데이터가 insert 혹은 update 될때 자동으로 현재시간 값이 들어감
    private Timestamp createdDate;


}

