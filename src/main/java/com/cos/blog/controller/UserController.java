package com.cos.blog.controller;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;


// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/~ 이하 경로들은 허용
// 그냥 주소가 "/" 이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/** 이하 경로들 허용

@Controller
@Configuration
public class UserController {

    @Value("${cos.key:'cos1234'}")
    private String cosKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @GetMapping("/auth/joinForm")
    public String joinForm(){

        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm(){

        return "user/loginForm";
    }

    // 카카오 로그인 인증
    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) { // @ResponseBody 데이터를 리턴해주는 컨트롤러 매소드 라는 뜻

        // POST방식으로 key= value 데이터 요청 (카카오쪽으로)


        RestTemplate rt = new RestTemplate();

        //HttpHeader (headers)오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); //-> Content-type 이 key : value 형식이다.

        //HttpBody (RequestBody) 오브젝트 생성
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "b4d98f4bb5b2ef0ce13957cb06c175d3");
        params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
        params.add("code",code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답 받음
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        //
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
             oauthToken = objectMapper.readValue(response.getBody(),OAuthToken.class);
        }catch (JsonMappingException e){
            e.printStackTrace();
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }


        System.out.println("카카오 엑세스 토큰 : "+oauthToken.getAccess_token());

        RestTemplate rt2 = new RestTemplate();

        //HttpHeader (headers)오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); //-> Content-type 이 key : value 형식이다.


        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2);

        // Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답 받음
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );

        System.out.println(response2.getBody());

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(),KakaoProfile.class);
        }catch (JsonMappingException e){
            e.printStackTrace();
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        //User 오브젝트 : username, password, email
        System.out.println("카카오 아이디(번호) : "+kakaoProfile.getId());
        System.out.println("카카오 이메일 : "+kakaoProfile.getKakao_account().getEmail());

        System.out.println("블로그서버 유저네임 : "+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
        System.out.println("블로그서버 이메일 : "+kakaoProfile.getKakao_account().getEmail());
        // UUID란 -> 중복되지않는 어떤 특정값을 만들어내는 알고리즘
        System.out.println("블로그서버 패스워드 "+cosKey);

        User kakaoUser = User.builder()
                        .username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
                        .password(cosKey)
                        .email(kakaoProfile.getKakao_account().getEmail())
                        .oauth("kakao")
                        .build();

        // 가입자 혹은 비가입자 인지 체크해서 처리
        User originUser = userService.회원찾기(kakaoUser.getUsername());

        // 비가입자이면 회원가입
        if (originUser.getUsername() == null){
            System.out.println("기존회원이 아닙니다. 자동 회원가입을 진행합니다. ");
            userService.회원가입(kakaoUser);
        }

        System.out.println("자동 로그인을 진행합니다.");
        // 이미 가입자이면 로그인 처리
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }


    @GetMapping("/user/updateForm")
    public String updateForm(){

        return "user/updateForm";
    }


}
