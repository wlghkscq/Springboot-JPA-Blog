package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;
// @Controller // 클라이언트 요청- > 응답 ( HTML 파일)

@RestController // 클라이언트 요청- > 응답 ( JSON data)
public class HttpControllerTest {

    private static final String TAG ="HttpControllerTest";

    @GetMapping("/http/lombok")
    public String lombokTest(){
        Member m = Member.builder().username("sdads").password("1234").email("fwfw@sff.con").build();
        System.out.println(TAG+"getter : "+m.getUsername());
        m.setUsername("coxxx");
        System.out.println(TAG+"setter : "+m.getUsername());
        return "lombok test 완료";
    }
    // 인터넷 웹브라우저 요청은 무조건 get 요청밖에 할 수 없다.
    //http://localhost:8080/http/get (select)
    @GetMapping("/http/get")      // MessageConverter(스프링부트)
    public String getTest(Member m){ // http://localhost:8080/http/get?id=1&username=ssar&password=1234&email=wlghkscq@naver.com

        return "get 요청"+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
    }

    //http://localhost:8080/http/post (insert)
    @PostMapping("/http/post")
    public String postTest(@RequestBody Member m){ // MessageConverter(스프링부트)
        return "post 요청"+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
    }

    //http://localhost:8080/http/put (update)
    @PutMapping("/http/put")
    public String putTest(@RequestBody Member m){
        return "put 요청"+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
    }

    //http://localhost:8080/http/delete (delete)
    @DeleteMapping("/http/delete")
    public String deleteTest(){
        return "delete 요청";
    }
}
