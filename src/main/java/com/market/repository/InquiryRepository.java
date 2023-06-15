package com.market.repository;

import com.market.entity.Inquiry;
import com.market.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

}
