package com.market.service;

import com.market.constant.InquiryStatus;
import com.market.dto.InquiryFormDto;
import com.market.entity.*;
import com.market.repository.InquiryImgRepository;
import com.market.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final InquiryImgService inquiryImgService;
    private final InquiryImgRepository inquiryImgRepository;

    public Long saveInquiry(InquiryFormDto inquiryFormDto, List<MultipartFile> inquiryImgFileList) throws Exception{

        inquiryFormDto.setRegDate(LocalDateTime.now());

        Inquiry inquiry = inquiryFormDto.createInquiry();
        inquiry.setStatus(InquiryStatus.PENDING);
        inquiry.setRegDate(inquiryFormDto.getRegDate());
        inquiryRepository.save(inquiry);

        //이미지 등록
        for(int i=0;i<inquiryImgFileList.size();i++){
            InquiryImg inquiryImg = new InquiryImg();
            inquiryImg.setInquiry(inquiry);

            inquiryImgService.saveInquiryImg(inquiryImg, inquiryImgFileList.get(i));
        }

        return inquiry.getId();
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

    public void deleteInquiry(Long inquiryId) {
        inquiryRepository.deleteById(inquiryId);
    }

}
