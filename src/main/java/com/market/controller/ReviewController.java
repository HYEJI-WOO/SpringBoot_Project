package com.market.controller;

import com.market.dto.ReviewDto;
import com.market.entity.Item;
import com.market.repository.ItemRepository;
import com.market.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;

    @PostMapping("/item/reviews")
    public String submitReview(Model model, @RequestParam("itemIdNum") Long itemId, @RequestParam("userId") String userId,
                               @RequestParam("content") String content, @RequestParam("photos") List<MultipartFile> reviewImgFileList) {

        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setContent(content);
        reviewDto.setWriter(userId);

        Optional<Item> item = itemRepository.findById(itemId);
        item.ifPresent(reviewDto::setItem);

        try {
            itemService.saveReview(reviewDto, reviewImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "문의글 등록 중 에러가 발생하였습니다.");
            return "redirect:/";
        }

        return "redirect:/";
    }

}
