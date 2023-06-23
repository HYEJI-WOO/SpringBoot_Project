package com.market.entity;

import com.market.constant.InquiryStatus;
import com.market.constant.InquiryType;
import com.market.constant.Role;
import com.market.dto.InquiryDto;
import com.market.dto.InquiryFormDto;
import com.market.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="inquiry")
@Getter
@Setter
@ToString(exclude = {"answer", "member"})
public class Inquiry {

    @Id
    @Column(name = "inquiry_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    private String writer;

    private LocalDateTime regDate;

    @Enumerated(EnumType.STRING)
    private InquiryType inquiryType;

    @Enumerated(EnumType.STRING)
    private InquiryStatus status;

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InquiryImg> imgs;

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Inquiry createInquiry(InquiryDto inquiryDto) {
        Inquiry inquiry = new Inquiry();
        inquiry.setTitle(inquiryDto.getTitle());
        inquiry.setContent(inquiryDto.getContent());
        inquiry.setWriter(inquiryDto.getWriter());
        inquiry.setInquiryType(inquiryDto.getInquiryType());
        inquiry.setImgs(inquiryDto.getImgs());
        inquiry.setRegDate(LocalDateTime.now());
        inquiry.setStatus(InquiryStatus.PENDING);

        Member member = new Member();
        member.setId(inquiryDto.getMemberId());
        inquiry.setMember(member);

        return inquiry;
    }

    public void updateInquiry(InquiryFormDto inquiryFormDto){
        this.title = inquiryFormDto.getTitle();
        this.content = inquiryFormDto.getContent();
    }

}
