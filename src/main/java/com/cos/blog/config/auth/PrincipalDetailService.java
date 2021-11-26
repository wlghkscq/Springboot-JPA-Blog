package com.cos.blog.config.auth;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service //Bean 등록
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 스프링이 로그인 요청을 가로챌 때 username, password 변수 2개를 가로채는데
    // password 부분 처리는 알아서 해준다.
    // 해당 username 이 db에 있는지만 확인해주면 됨
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username)
                .orElseThrow(
                        ()-> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다." + username)
                );
        return new PrincipalDetail(principal); // 시큐리티 세션에 유저정보가 저장 됨
    }
}
