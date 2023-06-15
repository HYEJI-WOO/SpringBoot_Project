package com.market.service;

import com.market.entity.Inquiry;
import com.market.entity.Member;
import com.market.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    public Inquiry saveInquiry(Inquiry inquiry) {
        return inquiryRepository.save(inquiry);
    }

    public List<Inquiry> getAllInquiries() {
        Sort sort = Sort.by(Sort.Direction.DESC, "regDate");
        return inquiryRepository.findAll(sort);
    }

    public Inquiry getInquiryById(Long inquiryId) {
        return inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));
    }

    public void update(Inquiry inquiry) {
        inquiryRepository.save(inquiry);
    }

//    public void deleteInquiry(Long inquiryId) {
//        // 문의 삭제 또는 상태 변경 등의 작업을 수행합니다.
//        inquiryRepository.delete(inquiryId);
//
//    }

//    public Page<Inquiry> getInquiriesByCategory(String category, Pageable pageable) {
//        return inquiryRepository.findByCategory(category, pageable);
//    }

}
