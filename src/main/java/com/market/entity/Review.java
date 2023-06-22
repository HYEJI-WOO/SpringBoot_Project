package com.market.entity;

import com.market.constant.InquiryStatus;
import com.market.dto.InquiryDto;
import com.market.dto.ReviewDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "review")
@Getter
@Setter
public class Review {

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Lob
    private String content;

    private String writer;

    private LocalDateTime regDate;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImg> imgs;

    public static Review createReview(ReviewDto reviewDto) {
        Review review = new Review();
        review.setContent(reviewDto.getContent());
        review.setWriter(reviewDto.getWriter());
        review.setItem(reviewDto.getItem());
        review.setRegDate(reviewDto.getRegDate());

        return review;
    }

}
