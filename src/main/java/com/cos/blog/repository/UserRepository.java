package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 자동으로 Bean 등록 -> @Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer> { // User 테이블을 관리하는 레파지토리 그리고 User 테이블의 pk 데이터타입은 Integer 이다.

    // select * from user where username = ?;
    Optional<User> findByUsername(String username);

}


// Jpa Naming 쿼리 전략
// select * from user where username ?  and password ? ;
// User findByUsernameAndPassword(String username, String password);

