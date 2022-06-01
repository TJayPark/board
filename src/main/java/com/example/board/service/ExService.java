package com.example.board.service;

import com.example.board.auth.MyUserDetail;
import com.example.board.entity.User;
import com.example.board.repository.ExRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
@Slf4j
public class ExService implements UserDetailsService {
    private final ExRepository repository;

    @Transactional
    public void joinUser(User user){
        System.out.println("444444");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println("555555");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("66666");
        repository.saveUser(user);
        System.out.println("7777");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //여기서 받은 유저 패스워드와 비교하여 로그인 인증
        User user = repository.findUserByEmail(email);
        return new MyUserDetail(user);
    }
}