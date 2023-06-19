package com.market.repository;

import com.market.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    Inquiry getInquiryById(Long inquiryId);
}
