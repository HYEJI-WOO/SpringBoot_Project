package com.market.dto;

import com.market.constant.InquiryStatus;
import com.market.constant.InquiryType;
import com.market.entity.Inquiry;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class InquiryFormDto {

    private Long id;

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotBlank(message = "내용 필수 입력 값입니다.")
    private String content;

    private String writer;

    private InquiryType inquiryType;

    private InquiryStatus status;

    private LocalDateTime regDate;

    private Long memberId;

    private List<InquiryImgDto> inquiryImgDtoList = new ArrayList<>();

    private List<Long> inquiryImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Inquiry createInquiry(){
        return modelMapper.map(this, Inquiry.class);
    }

    public static InquiryFormDto of(Inquiry inquiry){
        return modelMapper.map(inquiry, InquiryFormDto.class);
    }

}
