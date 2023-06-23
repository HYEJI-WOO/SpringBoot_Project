package com.market.service;

import com.market.dto.ReviewDto;
import com.market.entity.Answer;
import com.market.entity.Review;
import com.market.entity.ReviewImg;
import com.market.repository.AnswerRepository;
import com.market.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewImgService reviewImgService;

    public void saveReview(ReviewDto reviewDto, List<MultipartFile> reviewImgFileList) throws Exception {

        reviewDto.setRegDate(LocalDateTime.now());
        System.out.println("사이즈다 :" + reviewImgFileList.size());

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


//    public List<Review> getReviewById(Long itemId) {
//        List<Review> reviews = reviewRepository.findByItemId(itemId);
//        return reviews;
//    }

    public List<Review> getReviewById(Long itemId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "regDate"); // regDate를 내림차순으로 정렬하는 Sort 객체 생성
        List<Review> reviews = reviewRepository.findByItemIdOrderByRegDateDesc(itemId, sort); // 정렬 방식을 적용하여 리뷰 가져오기
        return reviews;
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}

