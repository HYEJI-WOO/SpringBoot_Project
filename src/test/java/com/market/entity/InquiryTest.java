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

        // Associate the answer with the inquiry
        answer.setInquiry(inquiry);
        inquiry.setAnswer(answer);

        // Create photos
        List<InquiryPhoto> photos = new ArrayList<>();

        InquiryPhoto photo1 = new InquiryPhoto();
        photo1.setFilePath("/path/to/photo1.jpg");
        photo1.setInquiry(inquiry);
        photos.add(photo1);

        InquiryPhoto photo2 = new InquiryPhoto();
        photo2.setFilePath("/path/to/photo2.jpg");
        photo2.setInquiry(inquiry);
        photos.add(photo2);

        // Associate the photos with the inquiry
        inquiry.setPhotos(photos);

        // Verify the associations
        Assertions.assertEquals(answer, inquiry.getAnswer());
        Assertions.assertEquals(inquiry, answer.getInquiry());
        Assertions.assertEquals(2, inquiry.getPhotos().size());
        Assertions.assertTrue(inquiry.getPhotos().contains(photo1));
        Assertions.assertTrue(inquiry.getPhotos().contains(photo2));
        Assertions.assertEquals(member, inquiry.getMember());
    }
}
