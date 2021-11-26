package com.cos.blog.controller.api;


import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;

@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    // 게시물 작성
    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal){ // @RequestBody요청을 json 으로 받기떄문에
        boardService.글쓰기(board, principal.getUser()); // board = title, content, getUser = 누가 작성했는지를 들고옴
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }




    // 게시물 삭제
    @DeleteMapping("/api/board{id}")
    public ResponseDto<Integer> deleteById(@PathVariable int id){
        boardService.글삭제하기(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // 게시물 수정
    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board){
        boardService.글수정하기(id,board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);

    }


    // 댓글 작성
    // 데이터를 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다
    // dto를 사용하지 않은 이유는
    @PostMapping("/api/board/{boardId}/reply")
   public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto){ // @RequestBody요청을 json 으로 받기떄문에
        boardService.댓글쓰기(replySaveRequestDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }


    // 댓글 삭제
    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> replyDelete(@PathVariable int replyId){
        boardService.댓글삭제(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }


}
