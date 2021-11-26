package com.cos.blog.handler;


import com.cos.blog.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice // 모든 Exception 예외가 발생하면 이 GlobalExceptionHandler 클래스가 실행됨
@RestController
public class GlobalExceptionHandler {
 // Exception > IllegalArgumentException, NullPointerException 둘 다 포함한다. Exception 는 예외처리리래스의 최상위 클래스
    @ExceptionHandler(value = Exception.class) //Exception 발생하면 그 error 를 e에 전달해줌
    public ResponseDto<String> handleArgumentException(Exception e){
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());

    }



}
