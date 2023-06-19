package com.market.repository;

import com.market.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByInquiryId(Long inquiryId);

    @Query("SELECT COUNT(a) FROM Answer a WHERE a.inquiry.id = :inquiryId")
    int getCountById(@Param("inquiryId") Long inquiryId);
}
