package com.market.controller;

import com.market.dto.InquiryDto;
import com.market.dto.MemberFormDto;
import com.market.entity.Inquiry;
import com.market.entity.Member;
import com.market.repository.MemberRepository;
import com.market.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("/inquiry")
@Controller
@RequiredArgsConstructor
public class InquiryController {

    private final MemberRepository memberRepository;
    private final InquiryService inquiryService;


    @GetMapping(value = "/list")
    public String inquiryList(Model model) {
        List<Inquiry> inquiries = inquiryService.getAllInquiries();

        model.addAttribute("inquiries", inquiries);
        return "inquiry/list";
    }

//    @GetMapping(value = "/list")
//    public String inquiryList(Model model,
//                              @RequestParam(defaultValue = "0") int page,
//                              @RequestParam(defaultValue = "10") int size,
//                              @RequestParam(required = false) String category) {
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "regDate");
//        Page<Inquiry> inquiryPage;
//
//        if (category != null) {
//            inquiryPage = inquiryService.getInquiriesByCategory(category, pageable);
//        } else {
//            inquiryPage = inquiryService.getAllInquiries(pageable);
//        }
//
//        model.addAttribute("inquiryPage", inquiryPage);
//
//        return "inquiry/list";
//    }


    @GetMapping(value = "/create")
    public String inquiryCreateForm(Model model, Principal principal) {
        String userId = principal.getName();
        model.addAttribute("userId", userId);
        model.addAttribute("inquiryDto", new InquiryDto());
        return "inquiry/createForm";
    }

    @PostMapping(value = "/create")
    public String createInquiry(@Valid InquiryDto inquiryDto, Model model) {
        String email = inquiryDto.getWriter();
        Member member = memberRepository.findByEmail(email);
        Long memberId = member.getId();
        inquiryDto.setMemberId(memberId);
        System.out.println(memberId);
        Inquiry inquiry = Inquiry.createInquiry(inquiryDto);
        inquiryService.saveInquiry(inquiry);

        return "redirect:/inquiry/list";
    }

    @GetMapping(value = "/{id}")
    public String getInquiry(@PathVariable("id") Long inquiryId, Model model, Principal principal) {
        String username = principal.getName();
        Inquiry inquiry = inquiryService.getInquiryById(inquiryId);
        model.addAttribute("inquiry", inquiry);
        model.addAttribute("username", username);
        return "inquiry/detail";
    }

    @PostMapping(value = "/modify")
    public String modify(@ModelAttribute InquiryDto inquiryDto, @RequestParam Long inquiryId) {
        Inquiry inquiry = inquiryService.getInquiryById(inquiryId);
        inquiry.setTitle(inquiryDto.getTitle());
        inquiry.setContent(inquiryDto.getContent());
        inquiryService.update(inquiry);

        return "redirect:/inquiry/list";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("inquiryId") String inquiryId) {
        Long convertedId = Long.parseLong(inquiryId);

        System.out.println("======================");
        System.out.println(inquiryId);
        System.out.println("======================");
        // 문의 삭제 로직을 구현합니다.

        // 삭제 후 어떤 페이지로 이동할지 리다이렉트 경로를 반환합니다.
        return "redirect:/inquiry/list";
    }




}
