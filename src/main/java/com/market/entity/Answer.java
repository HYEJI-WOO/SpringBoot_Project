package com.market.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "answer")
@Getter
@Setter
@ToString
public class Answer {

    @Id
    @Column(name = "answer_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id")
    private Inquiry inquiry;

    @Lob
    private String content;

    private String answerer;

    private LocalDateTime answerDate;
}
