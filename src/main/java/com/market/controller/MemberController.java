package com.market.controller;

import com.google.gson.Gson;
import com.market.dto.MemberFormDto;
import com.market.entity.Member;
import com.market.repository.MemberRepository;
import com.market.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Properties;
import java.util.Set;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    Session session;

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

    @PostMapping(value = "/findInfo")
    @ResponseBody
    public String findInfo(@RequestParam String email) {
        Member member = memberRepository.findByEmail(email);
        Gson gson = new Gson();

        if (member != null) {
            return gson.toJson(true);
        } else {
            return gson.toJson(false);
        }
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

    @GetMapping(value = "/findId")
    public String findIdForm() {
        return "member/findIdForm";
    }

    @PostMapping("/checkInfoId")
    @ResponseBody
    public String checkInfoId(@RequestParam String name, @RequestParam String phone) {
        Member member = memberRepository.findByNameAndPhone(name, phone);
        Gson gson = new Gson();

        if (member != null) {
            return gson.toJson(true);
        } else {
            return gson.toJson(false);
        }
    }

    @PostMapping("/sendCertificationNumberId")
    @ResponseBody
    public String sendCertificationNumberId() {
        String certificationNumber = String.format("%06d", (int) (Math.random() * 1000000));
        System.out.println(certificationNumber);
        Gson gson = new Gson();
        return gson.toJson(certificationNumber);
    }

    @PostMapping("/findId")
    @ResponseBody
    public String findId(@RequestParam String name, @RequestParam String phone) {
        Member member = memberRepository.findByNameAndPhone(name, phone);
        Gson gson = new Gson();
        return gson.toJson(member.getEmail());
    }

    @GetMapping("/idCheckForm")
    public String idCheckForm(@RequestParam String id, Model model) {
        model.addAttribute("id", id);
        return "member/idCheckForm";
    }

    @GetMapping(value = "/findPwd")
    public String findPwdForm() {
        return "member/findPwdForm";
    }

    String SetEmail = "";

    @PostMapping("/checkInfoPwd")
    @ResponseBody
    public String checkInfoPwd(@RequestParam String name, @RequestParam String email) {
        Member member = memberRepository.findByNameAndEmail(name, email);
        Gson gson = new Gson();

        if (member != null) {
            SetEmail = email;
            return gson.toJson(true);
        } else {
            return gson.toJson(false);
        }
    }

//    @PostMapping("/sendCertificationNumberPwd")
//    @ResponseBody
//    public String sendCertificationNumberPwd() {
//        String certificationNumber = String.format("%06d", (int) (Math.random() * 1000000));
//        System.out.println(SetEmail);
//        System.out.println(certificationNumber);
//        Gson gson = new Gson();
//        return gson.toJson(certificationNumber);
//    }

    @PostMapping("/sendCertificationNumberPwd")
    @ResponseBody
    public String sendCertificationNumberPwd() {
        String certificationNumber = String.format("%06d", (int) (Math.random() * 1000000));

        // JavaMail 설정
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true"); // 인증 사용

        // 구글 계정 정보
        String username = "whj1939@gmail.com";
        String password = "xx";

        // 인증 정보
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        // 세션 생성
        Session session = Session.getInstance(properties, authenticator);

        try {
            // MimeMessage 생성
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("whj1939@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(SetEmail));
            message.setSubject("Market 인증번호입니다.");
            message.setText("인증번호: " + certificationNumber);

            // 이메일 전송
            Transport.send(message);

            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Failed to send email.";
        }

        Gson gson = new Gson();
        return gson.toJson(certificationNumber);
    }

    @GetMapping("/pwdChangeForm")
    public String pwdChangeForm(@RequestParam String id, Model model) {
        model.addAttribute("id", id);
        return "member/pwdChangeForm";
    }

    @PostMapping("/pwdChange")
    public String pwdChange(@ModelAttribute MemberFormDto memberFormDto, BindingResult bindingResult) {
        String id = memberFormDto.getEmail();
        String password = memberFormDto.getPassword();
        Member member = memberService.getMemberByUsername(id);
        member.setPassword(passwordEncoder.encode(password));
        memberService.update(member);
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
