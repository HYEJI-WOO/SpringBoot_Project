package com.market.repository;

import com.market.entity.Inquiry;
import com.market.entity.InquiryImg;
import com.market.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryImgRepository extends JpaRepository<InquiryImg, Long> {

    List<InquiryImg> findByInquiryIdOrderByIdAsc(Long inquiryId);

}