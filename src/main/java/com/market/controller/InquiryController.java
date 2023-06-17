package com.market.controller;

import com.google.gson.Gson;
import com.market.dto.InquiryDto;
import com.market.dto.InquiryFormDto;
import com.market.dto.ItemFormDto;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("/inquiry")
@Controller
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    private final MemberRepository memberRepository;


    @GetMapping(value = "/list")
    public String inquiryList(Model model) {
        List<Inquiry> inquiries = inquiryService.getAllInquiries();

        model.addAttribute("inquiries", inquiries);
        return "inquiry/list";
    }

    @GetMapping(value = "/create")
    public String inquiryForm(Model model, Principal principal) {
        String userId = principal.getName();
        model.addAttribute("userId", userId);
        model.addAttribute("inquiryFormDto", new InquiryFormDto());
        return "inquiry/inquiryForm";
    }

    @PostMapping(value = "/create")
    public String inquiryNew(@Valid InquiryFormDto inquiryFormDto, BindingResult bindingResult, Model model,
                             @RequestParam("inquiryImgFile") List<MultipartFile> inquiryImgFileList,
                             @RequestParam String writer){

        if(bindingResult.hasErrors()){
            return "inquiry/inquiryForm";
        }

        Member member = memberRepository.findByEmail(writer);  // 적절한 repository 메서드를 사용하여 Member 객체를 조회해야 합니다.
        Long memberId = member.getId();
        inquiryFormDto.setMemberId(memberId);

        try{
            inquiryService.saveInquiry(inquiryFormDto, inquiryImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "문의글 등록 중 에러가 발생하였습니다.");
            return "inquiry/inquiryForm";
        }
        return "redirect:/";
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

    @DeleteMapping(value = "/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable("id") Long inquiryId) {
        System.out.println(inquiryId);
        inquiryService.deleteInquiry(inquiryId);

        Gson gson = new Gson();
        return gson.toJson(true);
    }



}
