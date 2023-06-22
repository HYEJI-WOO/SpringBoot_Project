package com.market.service;

import com.market.constant.InquiryStatus;
import com.market.dto.InquiryFormDto;
import com.market.dto.InquiryImgDto;
import com.market.dto.ItemFormDto;
import com.market.dto.ItemImgDto;
import com.market.entity.*;
import com.market.repository.InquiryImgRepository;
import com.market.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Transactional(readOnly = true)
    public InquiryFormDto getInquiryById(Long inquiryId){

        List<InquiryImg> inquiryImgList = inquiryImgRepository.findByInquiryIdOrderByIdAsc(inquiryId);
        List<InquiryImgDto> inquiryImgDtoList = new ArrayList<>();
        for (InquiryImg inquiryImg : inquiryImgList) {
            InquiryImgDto inquiryImgDto = InquiryImgDto.of(inquiryImg);
            inquiryImgDtoList.add(inquiryImgDto);
        }

        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(EntityNotFoundException::new);
        InquiryFormDto inquiryFormDto = InquiryFormDto.of(inquiry);
        inquiryFormDto.setInquiryImgDtoList(inquiryImgDtoList);
        return inquiryFormDto;
    }

    public Inquiry getInquiryById2(Long inquiryId) {
        return inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));
    }

    public List<Inquiry> getAllInquiries() {
        Sort sort = Sort.by(Sort.Direction.DESC, "regDate");
        return inquiryRepository.findAll(sort);
    }

    public void update(Inquiry inquiry) {
        inquiryRepository.save(inquiry);
    }

    public void deleteInquiry(Long inquiryId) {
        inquiryRepository.deleteById(inquiryId);
    }


    public double getTotalInquiryCount() {
        return inquiryRepository.count();
    }

    public List<Inquiry> getInquiriesByPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Inquiry> inquiryPage = inquiryRepository.findAll(pageable);

        return inquiryPage.getContent();
    }

    public Optional<Inquiry> findById(Long inquiryId) {
        return inquiryRepository.findById(inquiryId);
    }

}