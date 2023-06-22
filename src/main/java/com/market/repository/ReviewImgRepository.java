package com.market.repository;

import com.market.entity.InquiryImg;
import com.market.entity.ReviewImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImgRepository extends JpaRepository<ReviewImg, Long> {

    List<ReviewImg> findByItemIdOrderByIdAsc(Long itemId);

//    List<ReviewImg> findByReviewIdOrderByIdAsc(Long reviewId);

}