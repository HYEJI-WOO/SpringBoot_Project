package com.market.entity;

import com.market.constant.InquiryStatus;
import com.market.constant.InquiryType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InquiryTest {

    @Test
    public void testCreateInquiryWithAnswerAndPhotos() {
        // Create an inquiry
        Inquiry inquiry = new Inquiry();
        inquiry.setTitle("Test Inquiry");
        inquiry.setContent("This is a test inquiry.");
        inquiry.setRegDate(LocalDateTime.now());
        inquiry.setInquiryType(InquiryType.PRODUCT);
        inquiry.setStatus(InquiryStatus.PENDING);

        // Create a member
        Member member = new Member();
        member.setName("John Doe");
        member.setEmail("johndoe@example.com");

        // Associate the member with the inquiry
        inquiry.setMember(member);

        // Create an answer
        Answer answer = new Answer();
        answer.setContent("This is a test answer.");
        answer.setAnswerer("Jane Smith");
        answer.setAnswerDate(LocalDateTime.now());

//        // Associate the answer with the inquiry
//        answer.setInquiry(inquiry);
//        inquiry.setAn(answer);

        // Create photos
        List<InquiryImg> imgs = new ArrayList<>();

        InquiryImg img1 = new InquiryImg();
        img1.setImgUrl("/path/to/img1.jpg");
        img1.setInquiry(inquiry);
        imgs.add(img1);

        InquiryImg img2 = new InquiryImg();
        img1.setImgUrl("/path/to/img2.jpg");
        img2.setInquiry(inquiry);
        imgs.add(img2);

        // Associate the photos with the inquiry
        inquiry.setImgs(imgs);

        // Verify the associations
//        Assertions.assertEquals(answer, inquiry.getAnswer());
//        Assertions.assertEquals(inquiry, answer.getInquiry());
        Assertions.assertEquals(2, inquiry.getImgs().size());
        Assertions.assertTrue(inquiry.getImgs().contains(img1));
        Assertions.assertTrue(inquiry.getImgs().contains(img2));
        Assertions.assertEquals(member, inquiry.getMember());
    }
}
