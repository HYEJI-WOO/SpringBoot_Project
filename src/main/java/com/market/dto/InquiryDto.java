package com.market.dto;

import com.market.constant.InquiryStatus;
import com.market.constant.InquiryType;
import com.market.entity.Answer;
import com.market.entity.InquiryPhoto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class InquiryDto {
    private Long memberId;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDate;
    private InquiryType inquiryType;
    private InquiryStatus status;
    private List<InquiryPhoto> photos;
    private Answer answer;
}
