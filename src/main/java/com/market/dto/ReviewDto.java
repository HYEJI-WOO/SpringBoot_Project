package com.market.dto;

import com.market.entity.Item;
import com.market.entity.Review;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReviewDto {

    private Long id; // 답변 ID

    private Item item; // 상품 ID

    private String content; // 답변 내용

    private String writer; // 답변 작성자

    private LocalDateTime regDate; // 답변 작성 일자

    private List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Review createInquiry(){
        return modelMapper.map(this, Review.class);
    }

    public static ReviewDto of(Review review){
        return modelMapper.map(review, ReviewDto.class);
    }
}
