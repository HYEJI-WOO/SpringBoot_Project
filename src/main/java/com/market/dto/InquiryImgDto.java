package com.market.dto;

import com.market.entity.InquiryImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class InquiryImgDto {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private static ModelMapper modelMapper = new ModelMapper();

    public static InquiryImgDto of(InquiryImg inquiryImg) {
        return modelMapper.map(inquiryImg, InquiryImgDto.class);
    }
}
