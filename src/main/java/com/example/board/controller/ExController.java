package com.example.board.controller;

import com.example.board.auth.MyUserDetail;
import com.example.board.auth.dto.SessionUser;

import com.example.board.entity.Role;
import com.example.board.entity.User;
import com.example.board.service.ExService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ExController {

    private final HttpSession httpSession;


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
        System.out.println(user);
//        user.setRole("USER");
        user.setRole(Role.USER);
        service.joinUser(user);
        return "redirect:/login";
    }

    /**
     * 유저 페이지
     * @param model
     * @param authentication
     * @return
     */
    @GetMapping("/")
    public String userAccess(Model model, Authentication authentication,@AuthenticationPrincipal OAuth2User oAuth2UserPrincipal) {
        System.out.println("hellllllllll");
        if(authentication==null){
            return "redirect:/login";
        }else{
            //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
//            try{
//                System.out.println("44444");
//                MyUserDetail userDetail = (MyUserDetail)authentication.getPrincipal();  //userDetail 객체를 가져옴
//                model.addAttribute("info", userDetail.getUsername());      //유저 이메일
//                System.out.println("44444");
//
//            }catch (ClassCastException e){
//                System.out.println("55555");
//                OAuthAttributes principal = (OAuthAttributes) authentication.getPrincipal();
//                model.addAttribute("info", principal.getName());      //유저 이메일
//                System.out.println("6666");
//            }
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String userName = oAuth2User.getAttribute("name");
            System.out.println(userName);
            model.addAttribute("info", userName);      //유저 이메일
            System.out.println("22222");
            return "redirect:/board/list";


        }

    }

    @GetMapping("/login/social")
    public String social(Model model, @LoginUser SessionUser user) {
        if(user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("userImg", user.getPicture());

        }

        return "social";

    }
}