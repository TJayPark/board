package com.example.board.controller;

import com.example.board.auth.MyUserDetail;
import com.example.board.entity.Role;
import com.example.board.entity.User;
import com.example.board.service.ExService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ExController {
    private final ExService service;

    /**
     * 회원가입 폼
     * @return
     */
    @GetMapping("/signUp")
    public String signUpForm() {
        return "signup";
    }

    /**
     * 회원가입 진행
     * @param user
     * @return
     */
    @PostMapping("/signUp")
    public String signUp(User user) {
        System.out.println("hellllllllll");
        System.out.println(user);
//        user.setRole("USER");
        user.setRole(Role.USER);
        System.out.println("22222");
        service.joinUser(user);
        System.out.println("3333333");
        return "redirect:/login";
    }

    /**
     * 유저 페이지
     * @param model
     * @param authentication
     * @return
     */
    @GetMapping("/")
    public String userAccess(Model model, Authentication authentication) {
        if(authentication==null){
            return "redirect:/login";
        }else{
            //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
            MyUserDetail userDetail = (MyUserDetail)authentication.getPrincipal();  //userDetail 객체를 가져옴
            model.addAttribute("info", userDetail.getUsername());      //유저 이메일
            return "redirect:/board/list";
        }

    }
}