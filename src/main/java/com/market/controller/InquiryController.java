package com.market.controller;

import com.google.gson.Gson;
import com.market.dto.InquiryDto;
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

//    @PostMapping(value = "/create")
//    public String createInquiry(@Valid InquiryDto inquiryDto, BindingResult bindingResult, Model model, @RequestParam("inquiryImgFile")List<MultipartFile> inquiryImgFileList) {
//        String email = inquiryDto.getWriter();
//
//        Member member = memberRepository.findByEmail(email);
//        Long memberId = member.getId();
//        inquiryDto.setMemberId(memberId);
//        System.out.println(memberId);
//        Inquiry inquiry = Inquiry.createInquiry(inquiryDto);
//        inquiryService.saveInquiry(inquiry);
//
//        return "redirect:/inquiry/list";
//    }

//    @PostMapping(value = "/admin/item/new")
//    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model, @RequestParam("itemImgFile")List<MultipartFile> itemImgFileList){
//
//        if(bindingResult.hasErrors()){
//            return "item/itemForm";
//        }
//
//        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
//            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 값 입니다.");
//            return "item/itemForm";
//        }
//
//        try{
//            itemService.saveItem(itemFormDto, itemImgFileList);
//        } catch (Exception e){
//            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
//            return "item/itemForm";
//        }
//        return "redirect:/";
//    }

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
