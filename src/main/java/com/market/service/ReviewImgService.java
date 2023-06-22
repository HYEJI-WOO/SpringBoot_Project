package com.market.service;

import com.market.entity.BaseEntity;
import com.market.entity.InquiryImg;
import com.market.entity.ReviewImg;
import com.market.repository.InquiryImgRepository;
import com.market.repository.ReviewImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewImgService {

    @Value("${reviewImgLocation}")
    private String reviewImgLocation;

    private final ReviewImgRepository reviewImgRepository;

    private final FileService fileService;

    public void saveReviewImg(ReviewImg reviewImg, MultipartFile reviewImgFile) throws Exception{
        String oriImgName = reviewImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(reviewImgLocation, oriImgName, reviewImgFile.getBytes());
            imgUrl = "/images/review/" + imgName;
        }

        reviewImg.updateReviewImg(oriImgName, imgName, imgUrl);
        reviewImgRepository.save(reviewImg);
    }

//    public void deleteInquiryImg(Long inquiryImgId) {
//        InquiryImg inquiryImg = inquiryImgRepository.findById(inquiryImgId)
//                .orElseThrow(EntityNotFoundException::new);
//
//        // 이미지 파일 삭제 (이 부분은 사용하는 스토리지 메커니즘에 따라 구현해야 합니다.)
//        String imgName = inquiryImg.getImgName();
//        if (!StringUtils.isEmpty(imgName)) {
//            String filePath = inquiryImgLocation + "/" + imgName;
//            fileService.deleteFile(filePath);
//        }
//
//        // 문의 이미지 삭제
//        inquiryImgRepository.delete(inquiryImg);
//    }

}
