package com.cos.blog.service;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service // 스프링이 컴포넌트 스캔을 통해서 Bean에 등록해줌 -> IoC(제어의 역전)를 해줌
public class BoardService {


    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;


//    private final UserRepository userRepository;

    @Transactional
    public void 글쓰기(Board board, User user){ // title, content
        board.setCount(0); // 조회수 0으로 일단 대체
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> 글목록(Pageable pageable){
        return boardRepository.findAll(pageable);
    }


    @Transactional(readOnly = true)
    public Board 글상세보기(int id){
        return boardRepository.findById(id)
                .orElseThrow(
                        ()-> new IllegalArgumentException("글 상세보기 실패 : 해당 아이디를 찾을 수 없습니다.")
                );
    }

    @Transactional
    public void 글삭제하기(int id){
        boardRepository.deleteById(id);

    }

    @Transactional
    public void 글수정하기(int id, Board requsetBoard) {
        Board board = boardRepository.findById(id)
                .orElseThrow(
                        ()-> new IllegalArgumentException("글 찾기 실패 : 해당 아이디를 찾을 수 없습니다.")
                ); // 영속화 완료 \
        board.setTitle(requsetBoard.getTitle()); // 요청한 글제목으로 title 변경
        board.setContent(requsetBoard.getContent()); // 요청한 내용으로 content 변경
        // 해당 메소드로 종료시에( =서비스가 종료될때 ) 트랜잭션이 종료 , 이때 더티체킹으로 자동 업데이트 됨 (db 쪽으로 flush 함)
    }


    @Transactional
    public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {

     replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());

    }

    @Transactional
    public void 댓글삭제(int replyId) {
        replyRepository.deleteById(replyId);
    }
}
