package com.cos.blog.service;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service // 스프링이 컴포넌트 스캔을 통해서 Bean에 등록해줌 -> IoC(제어의 역전)를 해줌
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;


    @Transactional(readOnly = true)
    public User 회원찾기(String username) {

        User user = userRepository.findByUsername(username).orElseGet(()->{
            return new User();
        });
        return user;

    }


    @Transactional
    public void 회원가입(User user){
        String rawPassword = user.getPassword(); // 실제 입력한 비밀번호
        String encPassword = encoder.encode(rawPassword); // 실제 입력한 비밀번호를 해쉬 암호화 인코딩
        user.setPassword(encPassword); // 해쉬 암호화된 비밀번호로 password를 변경한다.
        user.setRole(RoleType.USER); // RoleType을 USER로 변경
        userRepository.save(user); // user 테이블에 저장

    }

    @Transactional
    public void 회원수정(User user) {
        // 수정시에는 Jpa 영속성 컨텍스트에 User 오브젝트를 영속화 시키고, 영속화된 User 오브젝트를 수정
        // select를 해서 user 오브젝트를 db로 부터 가져오는 이유는 영속화하기 위해서
        // 영속화된 오브젝트를 변경하면 자동으로 db에서 update문을 날려준다.
        User persistance = userRepository.findById(user.getId()).orElseThrow(
                ()-> new IllegalArgumentException("회원 찾기 실패")
        );

        // 유효성 검사 (Validate 체크크) oauth 값이 없으면 수정 가능
       // 만약 oauth 가 없거나 비어있으면  (카카오로 로그인을 안하면) 비밀번호 변경 가능
        // 카카오 로그인 한 사람은 비밀번호 변경 불가능
       if (persistance.getOauth() == null || persistance.getOauth().equals("")){
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword);
            persistance.setPassword(encPassword);
            persistance.setEmail(user.getEmail());
        }


        // 회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit 이 자동으로 된다.
        // 영속화 된 persistance 객체의 변화가 감지되면 (더티체킹) 변화한 값들을 update 문으로 날려준다.
    }


}
