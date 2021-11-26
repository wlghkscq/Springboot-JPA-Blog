package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Timestamp;


// ORM -> Java(다른언어 다포함) Object -> 테이블로 매핑하는 (만들어주는) 기술

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // User 클래스가 MySQL에 테이블이 생성된다.
//@DynamicInsert // insert 시 null 값을 제외시켜준다
public class User {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라감 ( AUTO 증가 )
    // IDENTITY 는 Oracle -> 시퀀스번호   MySQL -> AUTO INCREMENT
    private int id;

    @Column(nullable = false, length = 100, unique = true) // unique = true 유일한 값, 고유값 시퀀스 를 가진다
    private String username; // 아이디

    @Column(nullable = false, length = 100) // 비밀번호 -> 해쉬(암호화)
    private String password;


    @Column(nullable = false, length = 50)
    private String email;

    // @ColumnDefault("user")
    //DB는 RoleType 이라는게없다
    @Enumerated(EnumType.STRING) // 따라서 String 타입지정
    private RoleType role; // Enum 사용 하는게 좋음,  타입 강제 RoleType -> ADMIN USER

    private String oauth; // kakao, google

    @CreationTimestamp // 시간이 자동입력
    private Timestamp createDate;


}
