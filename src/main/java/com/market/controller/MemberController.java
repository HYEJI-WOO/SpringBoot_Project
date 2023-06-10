package com.market.controller;

import com.market.dto.MemberFormDto;
import com.market.entity.Member;
import com.market.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "member/memberForm";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginMember() {
        return "member/memberLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "member/memberLoginForm";
    }

    @GetMapping(value = "/myPage")
    public String myPage(Model model, Principal principal) {
        String username = principal.getName();
        Member member = memberService.getMemberByUsername(username);
        model.addAttribute("info", member);
        return "member/myPage";
    }

    @PostMapping(value = "/modify")
    public String modify(@ModelAttribute MemberFormDto memberFormDto, Principal principal, Model model) {
        String username = principal.getName();
        Member member = memberService.getMemberByUsername(username);
        member.setPhone(memberFormDto.getPhone());
        member.setGender(memberFormDto.getGender());
        member.setBirthDay(memberFormDto.getBirthDay());
        member.setBirthMonth(memberFormDto.getBirthMonth());
        member.setBirthYear(memberFormDto.getBirthYear());
        member.setAddress1(memberFormDto.getAddress1());
        member.setAddress2(memberFormDto.getAddress2());

        memberService.update(member);
        model.addAttribute("info", member);

        return "member/myPage";
    }
}
