package com.cos.blog.config;


import com.cos.blog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // -> Bean 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것 (IoC 관리)
@EnableWebSecurity // 시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정주소("/auth/~")로 접근 하면 권한 및 인증을 미리 체크 하겠다는 뜻 .
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    // 해쉬 암호화
    @Bean // IoC
    public BCryptPasswordEncoder encodePWD(){

        return new BCryptPasswordEncoder();
    }


    // 시큐리티가 대신 로그인 해주는데 그때 password 를 가로채기 하는데
    // 해당 password 가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
    // 같은 해쉬로 암호화해서 db 에 있는 해쉬랑 비교할 수 있음.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable() // csrf 토큰 비활성화 (테스트 시)
                .authorizeRequests()
                    .antMatchers("/","/auth/**", "/js/**", "/css/**","/image/**", "/dummy/**") //"/', "/auth/~" 이하 경로로 들어오는 건
                    .permitAll() // 모두 인증 없이 접속 허용
                    .anyRequest() // 위 /auth/~ 이하경로가 아닌 모든 경로는
                    .authenticated() // 인증을 받아야한다.
                .and()
                    .formLogin()
                    .loginPage("/auth/loginForm") // 인증이 되지않은 로그인 요청은 무조건 loginForm으로 온다
                    .loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티 해당 주소로 요청오는 로그인을 가로채서 대신 로그인한다
                    .defaultSuccessUrl("/"); // 로그인 성공 시 "/" 으로 온다

    }
}
