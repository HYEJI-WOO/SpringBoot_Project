package com.market.dto;

import com.market.entity.Inquiry;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.LongType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerDto {

    private Long id; // 답변 ID

    private Inquiry inquiry; // 문의 ID

    private String content; // 답변 내용

    private String answerer; // 답변 작성자

    private LocalDateTime answerDate; // 답변 작성 일자
}

