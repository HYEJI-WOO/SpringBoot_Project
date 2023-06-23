package com.market.service;

import com.market.dto.ReviewDto;
import com.market.entity.Review;
import com.market.entity.ReviewImg;
import com.market.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewImgService reviewImgService;

    public void saveReview(ReviewDto reviewDto, List<MultipartFile> reviewImgFileList) throws Exception {

        reviewDto.setRegDate(LocalDateTime.now());

        Review review = reviewDto.createInquiry();
        reviewRepository.save(review);

        // 이미지 등록
        for (MultipartFile file : reviewImgFileList) {
            if (!file.isEmpty()) {
                ReviewImg reviewImg = new ReviewImg();
                reviewImg.setReview(review);
                reviewImgService.saveReviewImg(reviewImg, file);
            }
        }
    }

    public int getCountById(Long itemId) {
        return reviewRepository.getCountById(itemId);
    }

    public List<Review> getReviewById(Long itemId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "regDate");
        List<Review> reviews = reviewRepository.findByItemIdOrderByRegDateDesc(itemId, sort);
        return reviews;
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}

