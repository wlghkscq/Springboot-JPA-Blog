package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.transaction.Transactional;
import java.util.List;

// html 파일이 아니라 JSON data 를 리턴해주는 controller
@RestController
public class DummyControllerTest {

    @Autowired // DI 의존성 주입
    private UserRepository userRepository;


    // 회원 정보 삭제
    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            return "삭제에 실패하였습니다. 해당 id : "+id+"는 DB에 존재하지 않습니다.";
        }

        return "삭제되었습니다. id" + id;

    }


    // 회원 정보 업데이트
    @Transactional // 메소드 종료시  db 값 변경을 확인하고 변경되었으면 자동 update commit
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // json 데이터 요청 -> java object로 변환해서 받아줌
        System.out.println("id : " + id);
        System.out.println("pw : " + requestUser.getPassword());
        System.out.println("email : " + requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow( // 해당 id를 찾아서 user에 저장
                () -> new NullPointerException("아이다가 존재하지 않습니다.")
        );
        // requestUser.getPassword() = 클라이언트 웹브라우저에서 유저가 요청한 (변경한) password 값을 가져온다
        user.setPassword(requestUser.getPassword()); //password 를 set 변경하겠다 requestUser.getPassword()으로
        user.setEmail(requestUser.getEmail());

        // userRepository.save(user);

        // 더티체킹

        return user;
    }


    // 전체 회원 조회
    // http://localhost:8000/blog/dummy/user
    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    // 페이징
    // 한 페이지당 2건의 데이터를 리턴
    @GetMapping("/dummy/user")
    public Page<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<User> pagingUser = userRepository.findAll(pageable);


        List<User> users = pagingUser.getContent();
        return pagingUser;

    }


    // 해당 id의 회원 상세 정보 조회
    // {id} 주소로 파라미터를 전달받을수 있음
    // http://localhost:8000/blog/dummy/user/id숫자
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {
        // 만약에 user/데이터에없는 id숫자 를 요청하면 db에서 못찾아오게 되서 user가 null이 될거 아냐?
        // 그럼 return null 이 리턴되자나 그럼 문제가 있지 않을까?
        // Optional 로 너의 user 객체를 감싸서 가져올테니 null 인지 아닌지 판단해서 리턴해줘
        User user = userRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 유저는 존재하지 않습니다. id : " + id) // 람다식
        );
        // 요청은 웹브라우저에서 하는데
        // user 객체 = 자바 오브젝트를 리턴
        // user 객체를 웹브라우저가 이해할수있는 json 으로 변환해줘야함
        // 스프링부트 = MessageConverter 라는 애가 응답시 자동 작동
        // 만약에 자바 오브젝트를 리턴하게되면 MessageConverter Jackson 라이브러리를 호출해서
        // user 오브젝트를 json으로 변환해서 브라우저로 던져준다.
        return user;
    }


    // 회원 등록
    // http://localhost:8000/blog/dummy/join(요청)
    // http 의 body 에 username, password, email 데이터를 가지고 (요청)
    @PostMapping("/dummy/join")
    public String join(User user) { // key = value (약속된 규칙)


        System.out.println("id : " + user.getId());
        System.out.println("username : " + user.getUsername());
        System.out.println("password : " + user.getPassword());
        System.out.println("email : " + user.getEmail());
        System.out.println("role : " + user.getRole());
        System.out.println("createdDate : " + user.getCreateDate());

        user.setRole(RoleType.USER);

        userRepository.save(user);
        return "회원가입이 완료되었습니다.";

    }

}
