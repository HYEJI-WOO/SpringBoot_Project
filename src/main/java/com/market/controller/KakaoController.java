package com.market.controller;

import com.market.dto.MemberFormDto;
import com.market.entity.Member;
import com.market.repository.MemberRepository;
import com.market.service.KakaoService;
import com.market.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/members")
public class KakaoController {

    @Autowired
    KakaoService kakaoService;

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/do")
    public String loginPage() {
        return "kakao/login";
    }

//    @GetMapping("/kakao")
//    public String getCI(@RequestParam String code, Model model) throws IOException {
//        System.out.println("code = " + code);
//        String access_token = kakaoService.getToken(code);
//        Map<String, Object> userInfo = kakaoService.getUserInfo(access_token);
//        model.addAttribute("code", code);
//        model.addAttribute("access_token", access_token);
//        model.addAttribute("userInfo", userInfo);
//
//        return "index";
//    }

    @GetMapping("/kakao")
    public String handleKakaoCallback(@RequestParam String code, Model model) throws IOException {
        System.out.println("code = " + code);
        String access_token = kakaoService.getToken(code);
        Map<String, Object> userInfo = kakaoService.getUserInfo(access_token);

        // 이메일 주소 가져오기
        String email = (String) userInfo.get("account_email");

        // 이메일로 이미 가입된 회원이 있는지 확인
        Member existingMember = memberRepository.findByEmail(email);
        System.out.println("rr " + existingMember);

        if (existingMember != null) {
            // 이미 가입된 회원인 경우, 로그인 처리
            // 로그인 처리 로직을 구현하세요 (예: 세션 설정 등)
            return "redirect:/"; // 로그인 후 이동할 경로
        } else {
            MemberFormDto memberFormDto = new MemberFormDto();
            memberFormDto.setEmail(email);
            model.addAttribute("memberFormDto", memberFormDto);

            return "member/memberForm"; // 회원가입 페이지로 이동할 경로
        }
    }


}

