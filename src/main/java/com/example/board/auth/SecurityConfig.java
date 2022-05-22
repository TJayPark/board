package com.example.board.auth;

import com.example.board.controller.LoginFailHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.example.board.service.ExService;
import lombok.RequiredArgsConstructor;


@EnableWebSecurity		//spring security 를 적용한다는 Annotation
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final ExService exService;
    /**
     * 규칙 설정
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/userAccess").hasRole("USER")
                .antMatchers("/signUp").anonymous()
                .and()
                .formLogin()
                .and()
                .csrf().disable();		//로그인 창
    }


    /**
     * 로그인 인증 처리 메소드
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(exService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
