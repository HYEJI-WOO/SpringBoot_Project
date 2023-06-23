package com.market.service;

import com.market.entity.InquiryImg;
import com.market.repository.InquiryImgRepository;
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
public class InquiryImgService {

    @Value("${inquiryImgLocation}")
    private String inquiryImgLocation;

    private final InquiryImgRepository inquiryImgRepository;

    private final FileService fileService;

    public void saveInquiryImg(InquiryImg inquiryImg, MultipartFile inquiryImgFile) throws Exception{
        String oriImgName = inquiryImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(inquiryImgLocation, oriImgName, inquiryImgFile.getBytes());
            imgUrl = "/images/inquiry/" + imgName;
        }

        inquiryImg.updateInquiryImg(oriImgName, imgName, imgUrl);
        inquiryImgRepository.save(inquiryImg);
    }

    public void updateInquiryImg(Long inquiryImgId, MultipartFile inquiryImgFile) throws Exception{
        if(!inquiryImgFile.isEmpty()){
            InquiryImg savedInquiryImg = inquiryImgRepository.findById(inquiryImgId).orElseThrow(EntityNotFoundException::new);

            if(!StringUtils.isEmpty(savedInquiryImg.getImgName())){
                fileService.deleteFile(inquiryImgLocation+"/"+savedInquiryImg.getImgName());
            }
            String oriImgName = inquiryImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(inquiryImgLocation, oriImgName, inquiryImgFile.getBytes());
            String imgUrl = "/images/inquiry/" + imgName;
            savedInquiryImg.updateInquiryImg(oriImgName, imgName, imgUrl);

            System.out.println("InquiryImg 업데이트 - inquiryImgId: " + inquiryImgId + ", oriImgName: " + oriImgName + ", imgName: " + imgName + ", imgUrl: " + imgUrl);
        }
    }

}
