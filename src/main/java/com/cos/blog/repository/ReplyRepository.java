package com.cos.blog.repository;

import com.cos.blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Modifying
    @Query(value = "INSERT INTO reply( userId, boardId, content, createdDate) VALUES(?, ?, ?, now())",nativeQuery = true)
    int mSave(int userId, int boardId, String content); // 타입이 int 인 이유 업데이트된 행의 개수를 리턴해줌


}
