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
    public String getInquiry(@PathVariable("id") Long inquiryId, Model model) {
        Inquiry inquiry = inquiryService.getInquiryById(inquiryId);
        model.addAttribute("inquiry", inquiry);
        return "inquiry/detail";
    }



}
